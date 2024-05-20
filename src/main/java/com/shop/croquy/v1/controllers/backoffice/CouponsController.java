package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.coupon.CouponStoreRequest;
import com.shop.croquy.v1.dto.backoffice.coupon.CouponUpdateRequest;
import com.shop.croquy.v1.entities.Coupon;
import com.shop.croquy.v1.services.backoffice.CouponsService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/coupons")
public class CouponsController {
    private final CouponsService couponsService;

    @GetMapping
    public ResponseEntity<Page<Coupon>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Coupon> paginatedCoupons = couponsService.getPaginatedCoupons(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedCoupons);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Coupon> show(@PathVariable String id) {
        Coupon coupon = couponsService.getCouponById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(coupon);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CouponStoreRequest request, Principal principal) {
        couponsService.storeCouponWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody CouponUpdateRequest request, @PathVariable String id) {
        couponsService.updateCouponById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        couponsService.destroyCouponById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        couponsService.toggleCouponStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
