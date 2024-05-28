package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.vendor.VendorStoreRequest;
import com.shop.croquy.v1.dto.backoffice.vendor.VendorUpdateRequest;
import com.shop.croquy.v1.dto.web.AddressUpdateRequest;
import com.shop.croquy.v1.entities.Vendor;
import com.shop.croquy.v1.entities.address.VendorAddress;
import com.shop.croquy.v1.entities.media.VendorLogo;
import com.shop.croquy.v1.services.backoffice.VendorsService;

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
@RequestMapping(path = "/v1/backoffice/vendors")
public class VendorsController {
    private final VendorsService vendorsService;

    @GetMapping
    public ResponseEntity<Page<Vendor>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Vendor> paginatedVendors = vendorsService.getPaginatedVendors(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedVendors);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Vendor> show(@PathVariable String id) {
        Vendor state = vendorsService.getVendorById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody VendorStoreRequest request, Principal principal) {
        vendorsService.storeVendorWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody VendorUpdateRequest request, @PathVariable String id) {
        vendorsService.updateVendorById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        vendorsService.destroyVendorById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        vendorsService.toggleVendorStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<VendorLogo> changeLogo(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        VendorLogo vendorLogo = vendorsService.changeVendorLogoById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(vendorLogo);
    }

    @DeleteMapping(path = "/{id}/logo")
    public ResponseEntity<Object> removeLogo(@PathVariable String id) {
        vendorsService.destroyVendorLogoById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/address")
    public ResponseEntity<VendorAddress> updateAddress(
            @Valid @RequestBody AddressUpdateRequest request,
            @PathVariable String id,
            Principal principal
    ) {
        VendorAddress vendorAddress = vendorsService.updateVendorAddressById(request, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(vendorAddress);
    }

    @DeleteMapping(path = "/{id}/address")
    public ResponseEntity<Object> removeAddress(@PathVariable String id) {
        vendorsService.destroyVendorAddressById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
