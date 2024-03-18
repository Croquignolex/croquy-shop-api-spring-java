package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.User;
import org.springframework.data.domain.Page;

public interface IUsersService {
    Page<User> getPaginatedUsersByUsername(String username);
}
