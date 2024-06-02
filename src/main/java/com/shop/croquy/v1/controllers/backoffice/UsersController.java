package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dto.backoffice.user.UserStoreRequest;
import com.shop.croquy.v1.dto.backoffice.user.UserUpdateRequest;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.services.backoffice.UsersService;

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
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@RequestMapping(path = "/v1/backoffice/users")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<Page<User>> index(
            @RequestParam(defaultValue = "") String needle,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal
    ) {
        Page<User> paginatedUsers = usersService.getPaginatedUsers(page, size, needle, principal.getName());

        return ResponseEntity.status(HttpStatus.OK).body(paginatedUsers);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> show(@PathVariable String id) {
        User user = usersService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK.value()).body(user);
    }

    @PostMapping
    public ResponseEntity<Object> store(@Valid @RequestBody UserStoreRequest request, Principal principal) {
        usersService.storeUserWithCreator(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@Valid @RequestBody UserUpdateRequest request, @PathVariable String id) {
        usersService.updateUserById(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED.value()).build();
    }

    @PatchMapping(path = "/{id}/toggle")
    public ResponseEntity<Object> toggle(@PathVariable String id) {
        usersService.toggleUserStatusById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
