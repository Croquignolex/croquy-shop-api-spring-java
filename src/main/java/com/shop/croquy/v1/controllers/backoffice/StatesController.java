package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.services.backoffice.StatesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/states")
public class StatesController {
    private final StatesService statesService;

    @GetMapping
    public ResponseEntity<Page<State>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<State> paginatedStates = statesService.getPaginatedStates(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedStates);
    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<Country> show(@PathVariable String id) {
//        Country country = statesService.getCountryById(id);
//
//        return ResponseEntity.status(HttpStatus.OK.value()).body(country);
//    }
//
//    @PostMapping
//    public ResponseEntity<Object> store(@Valid @RequestBody CountryStoreRequest request, Principal principal) {
//        statesService.storeCountryWithCreator(request, principal.getName());
//
//        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
//    }
//
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<Object> update(@Valid @RequestBody CountryUpdateRequest request, @PathVariable String id) {
//        statesService.updateCountryById(request, id);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
//    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        statesService.destroyStateById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

//    @PatchMapping(path = "/{id}/toggle")
//    public ResponseEntity<Object> toggle(@PathVariable String id) {
//        statesService.toggleStatusById(id);
//
//        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
//    }
}
