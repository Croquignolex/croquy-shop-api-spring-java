package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.attribute.AttributeStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attribute.AttributeUpdateRequest;
import com.shop.croquy.v1.entities.Attribute;
import com.shop.croquy.v1.services.backoffice.AttributesService;
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
@RequestMapping(path = "/v1/backoffice/attributes")
public class AttributesController {
    private final AttributesService attributesService;

    @GetMapping
    public ResponseEntity<Page<Attribute>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<Attribute> paginatedAttributes = attributesService.getPaginatedAttributes(page, size, needle, sort, direction);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedAttributes);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Attribute> show(@PathVariable String id) {
        Attribute state = attributesService.getAttributeById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody AttributeStoreRequest request, Principal principal) {
        attributesService.storeAttributeWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody AttributeUpdateRequest request, @PathVariable String id) {
        attributesService.updateAttributeById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        attributesService.destroyAttributeById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        attributesService.toggleAttributeStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
