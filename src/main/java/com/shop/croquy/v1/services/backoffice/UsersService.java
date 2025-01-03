package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.user.UserStoreRequest;
import com.shop.croquy.v1.dto.backoffice.user.UserUpdateRequest;
import com.shop.croquy.v1.dto.backoffice.vendor.VendorUpdateRequest;
import com.shop.croquy.v1.entities.Vendor;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.helpers.GeneralHelper;
import com.shop.croquy.v1.repositories.UserPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.backoffice.interfaces.IUsersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService implements IUsersService {
    private final UserPagingAndSortingRepository userPagingAndSortingRepository;
    private final UserRepository userRepository;

    @Override
    public Page<User> getPaginatedUsers(int pageNumber, int pageSize, String needle, String sort, String direction, String username) {
        Pageable pageable = GeneralHelper.buildPageable(pageNumber, pageSize, sort, direction);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        Role role = user.getRole();

        Collection<Role> includedRoles = new ArrayList<>();

        switch (role) {
            case ADMIN -> includedRoles.add(Role.ADMIN);
            case SUPER_ADMIN -> {
                includedRoles.add(Role.SUPER_ADMIN);
                includedRoles.add(Role.ADMIN);
                includedRoles.add(Role.SELLER);
                includedRoles.add(Role.MANAGER);
            }
        }

        if(StringUtils.isNotEmpty(needle)) {
            return userPagingAndSortingRepository
                    .findAllByUsernameContainsOrFirstNameContainsOrLastNameContainsAndIdIsNotAndRoleIn(
                            needle,
                            needle,
                            needle,
                            user.getId(),
                            includedRoles,
                            pageable
                    );
        }

        return userPagingAndSortingRepository.findAllByIdIsNotAndRoleIn(user.getId(), includedRoles, pageable);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(USER_NOT_FOUND));
    }

    @Override
    public void storeUserWithCreator(UserStoreRequest request, String creatorUsername) {
        if(userRepository.findFistByUsername(request.getUsername()).isPresent()) {
            throw new DataIntegrityViolationException(USER_USERNAME_ALREADY_EXIST);
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);

        userRepository.save(request.toUser(creator));
    }

    @Override
    public void updateUserById(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(USER_NOT_FOUND));

        if(!new BCryptPasswordEncoder().matches(request.getOldPassword(), user.getPassword())) {
            throw new DataIntegrityViolationException(USER_WRONG_OLD_PASSWORD);
        }

        user.setRole(Role.getEnumFromString(request.getRole()));
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void toggleUserStatusById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(USER_NOT_FOUND));

        user.setEnabled(!user.getEnabled());

        userRepository.save(user);
    }
}