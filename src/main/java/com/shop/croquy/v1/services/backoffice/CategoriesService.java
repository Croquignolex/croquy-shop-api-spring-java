package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.category.CategoryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.category.CategoryUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.media.CategoryBanner;
import com.shop.croquy.v1.entities.media.CategoryLogo;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.helpers.ImageOptimisationHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.backoffice.interfaces.ICategoriesService;
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
public class CategoriesService implements ICategoriesService {
    private final CategoryPagingAndSortingRepository categoryPagingAndSortingRepository;  
    private final CategoryLogoRepository categoryLogoRepository;
    private final CategoryBannerRepository categoryBannerRepository;
    private final CategoryRepository categoryRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @Override
    public Page<Category> getPaginatedCategories(int pageNumber, int pageSize, String needle, String sort, String direction) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        return (StringUtils.isNotEmpty(needle))
                ? categoryPagingAndSortingRepository.findAllByNameContainsOrSlugContains(needle, needle, pageable)
                : categoryPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public List<Category> getAllEnabledCategoriesOrderByName() {
        return categoryRepository.findByEnabledOrderByName(true);
    }

    @Override
    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));
    }

    @Override
    public void storeCategoryWithGroupAndCreator(CategoryStoreRequest request, String creatorUsername) {
        var group = groupRepository.findById(request.getGroupId()).orElse(null);

        if(categoryRepository.findFistByNameAndGroup(request.getName(), group).isPresent()) {
            throw new DataIntegrityViolationException(CATEGORY_NAME_ALREADY_EXIST);
        }

        if(categoryRepository.findFistBySlugAndGroup(request.getSlug(), group).isPresent()) {
            throw new DataIntegrityViolationException(CATEGORY_SLUG_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        categoryRepository.save(request.toCategory(group, creator));
    }

    @Override
    public void updateCategoryById(CategoryUpdateRequest request, String id) {
        var group = groupRepository.findById(request.getGroupId()).orElse(null);

        if(categoryRepository.findFistByNameAndIdNotAndGroup(request.getName(), id, group).isPresent()) {
            throw new DataIntegrityViolationException(CATEGORY_NAME_ALREADY_EXIST);
        }

        if(categoryRepository.findFistBySlugAndIdNotAndGroup(request.getSlug() , id, group).isPresent()) {
            throw new DataIntegrityViolationException(CATEGORY_SLUG_ALREADY_EXIST);
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        category.setGroup(group);
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setSeoTitle(request.getSeoTitle());
        category.setSeoDescription(request.getSeoDescription());
        category.setDescription(request.getDescription());

        categoryRepository.save(category);
    }

    @Override
    public void toggleCategoryStatusById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        category.setEnabled(!category.getEnabled());

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void destroyCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        if(category.isNonDeletable()) {
            throw new DataIntegrityViolationException(CATEGORY_CAN_NOT_BE_DELETED);
        }

        if(category.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(category.getLogo().getPath(), mediaFolderPath);
            categoryLogoRepository.delete(category.getLogo());
        }

        if(category.getBanner() != null) {
            ImageOptimisationHelper.deleteFile(category.getBanner().getPath(), mediaFolderPath);
            categoryBannerRepository.delete(category.getBanner());
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryLogo changeCategoryLogoById(MultipartFile image, String id, String creatorUsername) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        CategoryLogo categoryLogo = category.getLogo();

        if(categoryLogo != null) ImageOptimisationHelper.deleteFile(category.getLogo().getPath(), mediaFolderPath);
        else categoryLogo = new CategoryLogo();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        categoryLogo.setSize(image.getSize());
        categoryLogo.setOriginalName(savedFileDic.get("name"));
        categoryLogo.setPath(savedFileDic.get("path"));
        categoryLogo.setCategory(category);
        categoryLogo.setCreator(creator);

        categoryLogoRepository.save(categoryLogo);

        return categoryLogo;
    }

    @Override
    public void destroyCategoryLogoById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        if(category.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(category.getLogo().getPath(), mediaFolderPath);
            categoryLogoRepository.delete(category.getLogo());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }

    @Override
    public CategoryBanner changeCategoryBannerById(MultipartFile image, String id, String creatorUsername) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        CategoryBanner categoryBanner = category.getBanner();

        if(categoryBanner != null) ImageOptimisationHelper.deleteFile(category.getLogo().getPath(), mediaFolderPath);
        else categoryBanner = new CategoryBanner();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        categoryBanner.setSize(image.getSize());
        categoryBanner.setOriginalName(savedFileDic.get("name"));
        categoryBanner.setPath(savedFileDic.get("path"));
        categoryBanner.setCategory(category);
        categoryBanner.setCreator(creator);

        categoryBannerRepository.save(categoryBanner);

        return categoryBanner;
    }

    @Override
    public void destroyCategoryBannerById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(CATEGORY_NOT_FOUND));

        if(category.getBanner() != null) {
            ImageOptimisationHelper.deleteFile(category.getBanner().getPath(), mediaFolderPath);
            categoryBannerRepository.delete(category.getBanner());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }
}