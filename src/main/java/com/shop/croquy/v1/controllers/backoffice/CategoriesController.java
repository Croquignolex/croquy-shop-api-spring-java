package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.category.CategoryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.category.CategoryUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.media.CategoryBanner;
import com.shop.croquy.v1.entities.media.CategoryLogo;
import com.shop.croquy.v1.services.backoffice.CategoriesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<Page<Category>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<Category> paginatedCategories = categoriesService.getPaginatedCategories(page, size, needle, sort, direction);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedCategories);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Category> show(@PathVariable String id) {
        Category state = categoriesService.getCategoryById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CategoryStoreRequest request, Principal principal) {
        categoriesService.storeCategoryWithGroupAndCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody CategoryUpdateRequest request, @PathVariable String id) {
        categoriesService.updateCategoryById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        categoriesService.destroyCategoryById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        categoriesService.toggleCategoryStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CategoryLogo> changeLogo(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        CategoryLogo categoryLogo = categoriesService.changeCategoryLogoById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(categoryLogo);
    }

    @DeleteMapping(path = "/{id}/logo")
    public ResponseEntity<Object> removeLogo(@PathVariable String id) {
        categoriesService.destroyCategoryLogoById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/banner", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CategoryBanner> changeBanner(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        CategoryBanner categoryLogo = categoriesService.changeCategoryBannerById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(categoryLogo);
    }

    @DeleteMapping(path = "/{id}/banner")
    public ResponseEntity<Object> removeBanner(@PathVariable String id) {
        categoriesService.destroyCategoryBannerById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
