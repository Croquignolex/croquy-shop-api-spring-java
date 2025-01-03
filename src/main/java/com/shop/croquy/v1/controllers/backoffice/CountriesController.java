package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.country.CountryStoreRequest;
import com.shop.croquy.v1.dto.backoffice.country.CountryUpdateRequest;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.media.CountryFlag;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.services.backoffice.CountriesService;

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
@RequestMapping(path = "/v1/backoffice/countries")
public class CountriesController {
    private final CountriesService countriesService;

    @GetMapping
    public ResponseEntity<Page<Country>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<Country> paginatedCountries = countriesService.getPaginatedCountries(page, size, needle, sort, direction);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedCountries);
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
        countriesService.destroyCountryById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        countriesService.toggleCountryStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/flag", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CountryFlag> changeFlag(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        CountryFlag countryFlag = countriesService.changeCountryFlagById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(countryFlag);
    }

    @DeleteMapping(path = "/{id}/flag")
    public ResponseEntity<Object> removeFlag(@PathVariable String id) {
        countriesService.destroyCountryFlagById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @GetMapping(path = "/{id}/states")
    public ResponseEntity<Page<State>> states(
            @PathVariable String id,
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<State> paginatedStatesByCountryId = countriesService.getPaginatedStatesByCountryId(page, size, needle, sort, direction, id);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedStatesByCountryId);
    }
}
