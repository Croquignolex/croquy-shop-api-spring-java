package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.brand.BrandStoreRequest;
import com.shop.croquy.v1.dto.backoffice.brand.BrandUpdateRequest;
import com.shop.croquy.v1.entities.Brand;
import com.shop.croquy.v1.entities.media.BrandLogo;
import com.shop.croquy.v1.services.backoffice.BrandsService;

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
@RequestMapping(path = "/v1/backoffice/brands")
public class BrandsController {
    private final BrandsService brandsService;

    @GetMapping
    public ResponseEntity<Page<Brand>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Brand> paginatedBrands = brandsService.getPaginatedBrands(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedBrands);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Brand> show(@PathVariable String id) {
        Brand state = brandsService.getBrandById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody BrandStoreRequest request, Principal principal) {
        brandsService.storeBrandWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody BrandUpdateRequest request, @PathVariable String id) {
        brandsService.updateBrandById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        brandsService.destroyBrandById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        brandsService.toggleBrandStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<BrandLogo> changeLogo(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        BrandLogo brandLogo = brandsService.changeBrandLogoById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(brandLogo);
    }

    @DeleteMapping(path = "/{id}/logo")
    public ResponseEntity<Object> removeLogo(@PathVariable String id) {
        brandsService.destroyBrandLogoById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
