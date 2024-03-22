package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.services.backoffice.ShopsService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/shops")
public class ShopsController {
    private final ShopsService shopsService;

    @GetMapping
    public ResponseEntity<Page<Shop>> list(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Shop> paginatedSopsResponse = shopsService.getPaginatedShops(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedSopsResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        boolean deleteByIdResponse = shopsService.deleteById(id);

        HttpStatus httpStatus = (deleteByIdResponse) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).build();
    }
}
