package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.category.CategoryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.category.CategoryUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.media.CategoryBanner;
import com.shop.croquy.v1.entities.media.CategoryLogo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    Page<Category> getPaginatedCategories(int pageNumber, int pageSize, String needle);
    List<Category> getAllEnabledCategoriesOrderByName();
    Category getCategoryById(String id);
    void storeCategoryWithGroupAndCreator(CategoryStoreRequest request, String creatorUsername);
    void updateCategoryById(CategoryUpdateRequest request, String id);
    void toggleCategoryStatusById(String id);
    void destroyCategoryById(String id);
    CategoryLogo changeCategoryLogoById(MultipartFile image, String id, String creatorUsername);
    CategoryBanner changeCategoryBannerById(MultipartFile image, String id, String creatorUsername);
    void destroyCategoryLogoById(String id);
    void destroyCategoryBannerById(String id); 
}
