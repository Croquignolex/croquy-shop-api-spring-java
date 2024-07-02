package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.group.GroupStoreRequest;
import com.shop.croquy.v1.dto.backoffice.group.GroupUpdateRequest;
import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.media.GroupBanner;
import com.shop.croquy.v1.entities.media.GroupLogo;
import com.shop.croquy.v1.services.backoffice.GroupsService;

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
@RequestMapping(path = "/v1/backoffice/groups")
public class GroupsController {
    private final GroupsService groupsService;

    @GetMapping
    public ResponseEntity<Page<Group>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<Group> paginatedGroups = groupsService.getPaginatedGroups(page, size, needle, sort, direction);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedGroups);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Group> show(@PathVariable String id) {
        Group state = groupsService.getGroupById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(state);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody GroupStoreRequest request, Principal principal) {
        groupsService.storeGroupWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody GroupUpdateRequest request, @PathVariable String id) {
        groupsService.updateGroupById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> destroy(@PathVariable String id) {
        groupsService.destroyGroupById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        groupsService.toggleGroupStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/logo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GroupLogo> changeLogo(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        GroupLogo groupLogo = groupsService.changeGroupLogoById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(groupLogo);
    }

    @DeleteMapping(path = "/{id}/logo")
    public ResponseEntity<Object> removeLogo(@PathVariable String id) {
        groupsService.destroyGroupLogoById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PatchMapping(path = "/{id}/banner", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<GroupBanner> changeBanner(
            @RequestPart MultipartFile image,
            @PathVariable String id,
            Principal principal
    ) {
        GroupBanner groupLogo = groupsService.changeGroupBannerById(image, id, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(groupLogo);
    }

    @DeleteMapping(path = "/{id}/banner")
    public ResponseEntity<Object> removeBanner(@PathVariable String id) {
        groupsService.destroyGroupBannerById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @GetMapping(path = "/{id}/categories")
    public ResponseEntity<Page<Category>> categories(
            @PathVariable String id,
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Page<Category> paginatedCategoriesByCountryId = groupsService.getPaginatedCategoriesByGroupId(page, size, needle, sort, direction, id);

        return ResponseEntity.status(HttpStatus.OK).body(paginatedCategoriesByCountryId);
    }
}
