package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.brand.BrandStoreRequest;
import com.shop.croquy.v1.dto.backoffice.brand.BrandUpdateRequest;
import com.shop.croquy.v1.entities.Brand;
import com.shop.croquy.v1.entities.media.BrandLogo;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IBrandsService {
    Page<Brand> getPaginatedBrands(int pageNumber, int pageSize, String needle);
    Brand getBrandById(String id);
    void storeBrandCreator(BrandStoreRequest request, String creatorUsername);
    void updateBrandById(BrandUpdateRequest request, String id);
    void toggleBrandStatusById(String id);
    void destroyBrandById(String id); 
    BrandLogo changeBrandLogoById(MultipartFile image, String id, String creatorUsername);
    void destroyBrandLogoById(String id);
}
