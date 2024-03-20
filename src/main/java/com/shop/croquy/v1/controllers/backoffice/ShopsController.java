package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.services.backoffice.ShopsService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/shops")
public class ShopsController {
    private final ShopsService shopsService;

    @GetMapping
    public ResponseEntity<Page<Shop>> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Shop> paginatedSopsResponse = shopsService.getPaginatedShops(page, size);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedSopsResponse);
    }
}
