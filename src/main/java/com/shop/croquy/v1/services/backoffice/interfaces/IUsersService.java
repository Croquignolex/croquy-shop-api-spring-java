package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.entities.User;
import org.springframework.data.domain.Page;

public interface IUsersService {
    Page<User> getPaginatedUsersByUsername(String username);
}
