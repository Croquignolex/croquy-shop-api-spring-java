package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.group.GroupStoreRequest;
import com.shop.croquy.v1.dto.backoffice.group.GroupUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.media.GroupBanner;
import com.shop.croquy.v1.entities.media.GroupLogo;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IGroupService {
    Page<Group> getPaginatedGroups(int pageNumber, int pageSize, String needle, String sort, String direction);
    List<Group> getAllEnabledGroupsOrderByName();
    Group getGroupById(String id);
    void storeGroupWithCreator(GroupStoreRequest request, String creatorUsername);
    void updateGroupById(GroupUpdateRequest request, String id);
    void toggleGroupStatusById(String id);
    void destroyGroupById(String id);
    GroupLogo changeGroupLogoById(MultipartFile image, String id, String creatorUsername);
    GroupBanner changeGroupBannerById(MultipartFile image, String id, String creatorUsername);
    void destroyGroupLogoById(String id);
    void destroyGroupBannerById(String id);
    Page<Category> getPaginatedCategoriesByGroupId(int pageNumber, int pageSize, String needle, String sort, String direction, String id);
}
