package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.services.backoffice.ShopsService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
@RequestMapping(path = "/v1/backoffice/shops")
public class ShopsController {
    private final ShopsService shopsService;

    @GetMapping
    public ResponseEntity<Page<Shop>> list() {
        return ResponseEntity.ok(shopsService.getPaginatedShops());
    }
}
