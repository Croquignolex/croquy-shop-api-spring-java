package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.brand.BrandStoreRequest;
import com.shop.croquy.v1.dto.backoffice.brand.BrandUpdateRequest;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.entities.Brand;
import com.shop.croquy.v1.entities.media.BrandLogo;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.helpers.ImageOptimisationHelper;
import com.shop.croquy.v1.repositories.*;
import com.shop.croquy.v1.services.interfaces.IBrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandsService implements IBrandService {
    private final BrandPagingAndSortingRepository brandPagingAndSortingRepository; 
    private final BrandLogoRepository brandLogoRepository;
    private final BrandRepository brandRepository;
    private final StateRepository stateRepository;
    private final UserRepository userRepository;

    @Value("${media.saving.directory}")
    private String mediaFolderPath;

    @Override
    public Page<Brand> getPaginatedBrands(int pageNumber, int pageSize, String needle) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize);

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return brandPagingAndSortingRepository.findAllByNameContainsOrSlugContainsOrCreatorIsIn(needle, needle, users, pageable);
        }

        return brandPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Brand getBrandById(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(BRAND_NOT_FOUND));
    }

    @Override
    public void storeBrandCreator(BrandStoreRequest request, String creatorUsername) {
        if(brandRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(BRAND_NAME_ALREADY_EXIST + request.getName());
        }

        if(brandRepository.findFistBySlug(request.getSlug()).isPresent()) {
            throw new DataIntegrityViolationException(BRAND_SLUG_ALREADY_EXIST + request.getSlug());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        brandRepository.save(request.toBrand(creator));
    }

    @Override
    public void updateBrandById(BrandUpdateRequest request, String id) {
        if(brandRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(BRAND_NAME_ALREADY_EXIST + request.getName());
        }

        if(brandRepository.findFistBySlugAndIdNot(request.getSlug() , id).isPresent()) {
            throw new DataIntegrityViolationException(BRAND_SLUG_ALREADY_EXIST + request.getSlug());
        }

        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(BRAND_NOT_FOUND));

        brand.setName(request.getName());
        brand.setSlug(request.getSlug());
        brand.setWebsite(request.getWebsite());
        brand.setSeoTitle(request.getSeoTitle());
        brand.setSeoDescription(request.getSeoDescription());
        brand.setDescription(request.getDescription());

        brandRepository.save(brand);
    }

    @Override
    public void toggleBrandStatusById(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(BRAND_NOT_FOUND));

        brand.setEnabled(!brand.getEnabled());

        brandRepository.save(brand);
    }

    @Override
    public void destroyBrandById(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(BRAND_NOT_FOUND));

        if(brand.isNonDeletable()) {
            throw new DataIntegrityViolationException(BRAND_CAN_NOT_BE_DELETED);
        }

        if(brand.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(brand.getLogo().getPath(), mediaFolderPath);
            brandLogoRepository.delete(brand.getLogo());
        }

        brandRepository.deleteById(id);
    }

    @Override
    public BrandLogo changeBrandLogoById(MultipartFile image, String id, String creatorUsername) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(COUNTRY_NOT_FOUND));

        BrandLogo brandLogo = brand.getLogo();

        if(brandLogo != null) ImageOptimisationHelper.deleteFile(brand.getLogo().getPath(), mediaFolderPath);
        else brandLogo = new BrandLogo();

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        Map<String, String> savedFileDic = ImageOptimisationHelper.saveFile(
                image,
                mediaFolderPath,
                1024 * 1024,
                List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE)
        );

        brandLogo.setSize(image.getSize());
        brandLogo.setOriginalName(savedFileDic.get("name"));
        brandLogo.setPath(savedFileDic.get("path"));
        brandLogo.setBrand(brand);
        brandLogo.setCreator(creator);

        brandLogoRepository.save(brandLogo);

        return brandLogo;
    }

    @Override
    public void destroyBrandLogoById(String id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(BRAND_NOT_FOUND));

        if(brand.getLogo() != null) {
            ImageOptimisationHelper.deleteFile(brand.getLogo().getPath(), mediaFolderPath);
            brandLogoRepository.delete(brand.getLogo());
        } else {
            throw new DataIntegrityViolationException(LOGO_NOT_FOUND);
        }
    }
}