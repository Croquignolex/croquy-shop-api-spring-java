package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.repositories.UserPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.backoffice.interfaces.IUsersService;

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
        includedRoles.add(Role.CUSTOMER);

        switch (role) {
            case ADMIN -> includedRoles.add(Role.ADMIN);
            case SUPER_ADMIN -> {
                includedRoles.add(Role.SUPER_ADMIN);
                includedRoles.add(Role.ADMIN);
            }
        }

        log.info("Users list requested by ===> " + user);

        return userPagingAndSortingRepository.findAllByIdIsNotAndRoleIn(user.getId(), includedRoles, pageable);
    }
}