package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.group.GroupCategoryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.group.GroupStoreRequest;
import com.shop.croquy.v1.dto.backoffice.group.GroupUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.entities.media.GroupBanner;
import com.shop.croquy.v1.entities.media.GroupLogo;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.helpers.ImageOptimisationHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.interfaces.IGroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupsService implements IGroupService {
    private final GroupPagingAndSortingRepository groupPagingAndSortingRepository; 
    private final CategoryPagingAndSortingRepository categoryPagingAndSortingRepository;
    private final GroupLogoRepository groupLogoRepository;
    private final GroupBannerRepository groupBannerRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @Override
    public Page<Group> getPaginatedGroups(int pageNumber, int pageSize, String needle) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize);

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return groupPagingAndSortingRepository.findAllByNameContainsOrSlugContainsOrCreatorIsIn(needle, needle, users, pageable);
        }

        return groupPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public List<Group> getAllEnabledGroupsOrderByName() {
        return groupRepository.findByEnabledOrderByName(true);
    }

    @Override
    public Group getGroupById(String id) {
        return groupRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));
    }

    @Override
    public void storeGroupCreator(GroupStoreRequest request, String creatorUsername) {
        if(groupRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(GROUP_NAME_ALREADY_EXIST + request.getName());
        }

        if(groupRepository.findFistBySlug(request.getSlug()).isPresent()) {
            throw new DataIntegrityViolationException(GROUP_SLUG_ALREADY_EXIST + request.getSlug());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        groupRepository.save(request.toGroup(creator));
    }

    @Override
    public void updateGroupById(GroupUpdateRequest request, String id) {
        if(groupRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(GROUP_NAME_ALREADY_EXIST + request.getName());
        }

        if(groupRepository.findFistBySlugAndIdNot(request.getSlug() , id).isPresent()) {
            throw new DataIntegrityViolationException(GROUP_SLUG_ALREADY_EXIST + request.getSlug());
        }

        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        group.setName(request.getName());
        group.setSlug(request.getSlug());
        group.setSeoTitle(request.getSeoTitle());
        group.setSeoDescription(request.getSeoDescription());
        group.setDescription(request.getDescription());

        groupRepository.save(group);
    }

    @Override
    public void toggleGroupStatusById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        group.setEnabled(!group.getEnabled());

        groupRepository.save(group);
    }

    @Override
    @Transactional
    public void destroyGroupById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        if(group.isNonDeletable()) {
            throw new DataIntegrityViolationException(GROUP_CAN_NOT_BE_DELETED);
        }

        if(group.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(group.getLogo().getPath(), mediaFolderPath);
            groupLogoRepository.delete(group.getLogo());
        }

        if(group.getBanner() != null) {
            ImageOptimisationHelper.deleteFile(group.getBanner().getPath(), mediaFolderPath);
            groupBannerRepository.delete(group.getBanner());
        }

        groupRepository.deleteById(id);
    }

    @Override
    public GroupLogo changeGroupLogoById(MultipartFile image, String id, String creatorUsername) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        GroupLogo groupLogo = group.getLogo();

        if(groupLogo != null) ImageOptimisationHelper.deleteFile(group.getLogo().getPath(), mediaFolderPath);
        else groupLogo = new GroupLogo();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        groupLogo.setSize(image.getSize());
        groupLogo.setOriginalName(savedFileDic.get("name"));
        groupLogo.setPath(savedFileDic.get("path"));
        groupLogo.setGroup(group);
        groupLogo.setCreator(creator);

        groupLogoRepository.save(groupLogo);

        return groupLogo;
    }

    @Override
    public void destroyGroupLogoById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        if(group.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(group.getLogo().getPath(), mediaFolderPath);
            groupLogoRepository.delete(group.getLogo());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }

    @Override
    public GroupBanner changeGroupBannerById(MultipartFile image, String id, String creatorUsername) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        GroupBanner groupBanner = group.getBanner();

        if(groupBanner != null) ImageOptimisationHelper.deleteFile(group.getLogo().getPath(), mediaFolderPath);
        else groupBanner = new GroupBanner();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        groupBanner.setSize(image.getSize());
        groupBanner.setOriginalName(savedFileDic.get("name"));
        groupBanner.setPath(savedFileDic.get("path"));
        groupBanner.setGroup(group);
        groupBanner.setCreator(creator);

        groupBannerRepository.save(groupBanner);

        return groupBanner;
    }

    @Override
    public void destroyGroupBannerById(String id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(GROUP_NOT_FOUND));

        if(group.getBanner() != null) {
            ImageOptimisationHelper.deleteFile(group.getBanner().getPath(), mediaFolderPath);
            groupBannerRepository.delete(group.getBanner());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }

    @Override
    public Page<Category> getPaginatedCategoriesByGroupId(int pageNumber, int pageSize, String needle, String id) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize);

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return categoryPagingAndSortingRepository.findAllByNameContainsOrCreatorIsInAndGroupId(needle, users, id, pageable);
        }

        return categoryPagingAndSortingRepository.findAllByGroupId(id, pageable);
    }

    @Override
    public void addCategoryWithCreator(GroupCategoryStoreRequest request, String id, String creatorUsername) {
        var group = groupRepository.findById(id).orElse(null);

        if(categoryRepository.findFistByNameAndGroup(request.getName(), group).isPresent()) {
            throw new DataIntegrityViolationException(CATEGORY_NAME_ALREADY_EXIST + request.getName());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        categoryRepository.save(request.toCategory(group, creator));
    }
}