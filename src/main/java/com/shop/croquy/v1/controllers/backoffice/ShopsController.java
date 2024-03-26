package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dao.backoffice.GenericResponse;
import com.shop.croquy.v1.dao.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.services.backoffice.ShopsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/shops")
public class ShopsController {
    private final ShopsService shopsService;

    @GetMapping
    public ResponseEntity<Page<Shop>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Shop> paginatedSopsResponse = shopsService.getPaginatedShops(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedSopsResponse);
    }

    @PostMapping
    public ResponseEntity<GenericResponse> store(@Valid @RequestBody ShopStoreRequest request, Principal principal) {
        GenericResponse createResponse = shopsService.create(request, principal.getName());

        return ResponseEntity.status(createResponse.getCode()).body(createResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<GenericResponse> delete(@PathVariable String id) {
        GenericResponse deleteByIdResponse = shopsService.deleteById(id);

        return ResponseEntity.status(deleteByIdResponse.getCode()).body(deleteByIdResponse);
    }
}
