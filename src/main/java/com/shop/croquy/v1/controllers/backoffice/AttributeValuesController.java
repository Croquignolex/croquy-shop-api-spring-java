package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueUpdateRequest;
import com.shop.croquy.v1.entities.AttributeValue;
import com.shop.croquy.v1.services.backoffice.AttributeValuesService;
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
@RequestMapping(path = "/v1/backoffice/attribute-values")
public class AttributeValuesController {
    private final AttributeValuesService attributeValuesService;

    @GetMapping
    public ResponseEntity<Page<AttributeValue>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<AttributeValue> paginatedAttributeValues = attributeValuesService.getPaginatedAttributeValues(page, size, needle);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedAttributeValues);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AttributeValue> show(@PathVariable String id) {
        AttributeValue state = attributeValuesService.getAttributeValueById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody AttributeValueStoreRequest request, Principal principal) {
        attributeValuesService.storeAttributeValueWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody AttributeValueUpdateRequest request, @PathVariable String id) {
        attributeValuesService.updateAttributeValueById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        attributeValuesService.destroyAttributeValueById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        attributeValuesService.toggleAttributeValueStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    } 
}
