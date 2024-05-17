package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.dto.backoffice.shop.ShopUpdateRequest;
import com.shop.croquy.v1.dto.web.AddressUpdateRequest;
import com.shop.croquy.v1.entities.Shop;
import com.shop.croquy.v1.entities.address.ShopAddress;
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
        Page<Shop> paginatedShops = shopsService.getPaginatedShops(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedShops);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Shop> show(@PathVariable String id) {
        Shop shop = shopsService.getShopById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(shop);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody ShopStoreRequest request, Principal principal) {
        shopsService.storeShopWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody ShopUpdateRequest request, @PathVariable String id) {
        shopsService.updateShopById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        shopsService.destroyShopById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        shopsService.toggleShopStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/address")
    public ResponseEntity<ShopAddress> updateAddress(
            @Valid @RequestBody AddressUpdateRequest request,
            @PathVariable String id,
            Principal principal
    ) {
        ShopAddress shopAddress = shopsService.updateShopAddressById(request, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(shopAddress);
    }

//    @DeleteMapping(path = "/{id}/flag")
//    public ResponseEntity<Object> removeFlag(@PathVariable String id) {
//        countriesService.destroyCountryFlagById(id);
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
//    }
}
