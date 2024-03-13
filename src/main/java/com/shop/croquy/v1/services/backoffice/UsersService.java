package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.repositories.UserPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService implements IUsersService {
    private final UserPagingAndSortingRepository userPagingAndSortingRepository;
    private final UserRepository userRepository;

    @Override
    public Page<User> getPaginatedUsersByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Role role = user.getRole();

        Pageable pageable = PageRequest.of(0, 2);

        Collection<Role> includedRoles = new ArrayList<>();
        includedRoles.add(Role.ROLE_CUSTOMER);

        switch (role) {
            case ROLE_ADMIN -> includedRoles.add(Role.ROLE_ADMIN);
            case ROLE_SUPER_ADMIN -> {
                includedRoles.add(Role.ROLE_SUPER_ADMIN);
                includedRoles.add(Role.ROLE_ADMIN);
            }
        }

        log.info("Users list requested by ===> " + user);

        return userPagingAndSortingRepository.findAllByIdIsNotAndRoleIn(user.getId(), includedRoles, pageable);
    }
}