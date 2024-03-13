package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.services.backoffice.UsersService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('admin')")
@RequestMapping(path = "/v1/backoffice/users")
//@PreAuthorize("hasAuthority('super_admin') or hasAuthority('admin')")
public class UsersController {
    private final UsersService usersService;

    @GetMapping
    public ResponseEntity<Page<User>> list(Principal principal) {
        return ResponseEntity.ok(usersService.getPaginatedUsersByUsername(principal.getName()));
    }
}
