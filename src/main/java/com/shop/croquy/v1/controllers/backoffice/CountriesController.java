package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.services.backoffice.CountriesService;
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
@RequestMapping(path = "/v1/backoffice/countries")
public class CountriesController {
    private final CountriesService countriesService;

    @GetMapping
    public ResponseEntity<Page<Country>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Country> paginatedCountriesResponse = countriesService.getPaginatedCountries(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedCountriesResponse);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Country> show(@PathVariable String id) {
        Country country = countriesService.getCountryById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(country);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody CountryStoreRequest request, Principal principal) {
        countriesService.storeCountryWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody CountryUpdateRequest request, @PathVariable String id) {
        countriesService.updateCountryById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        countriesService.destroyById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        countriesService.toggleStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
