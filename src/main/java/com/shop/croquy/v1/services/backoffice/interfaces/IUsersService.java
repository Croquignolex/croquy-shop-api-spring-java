package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.user.UserStoreRequest;
import com.shop.croquy.v1.dto.backoffice.user.UserUpdateRequest;
import com.shop.croquy.v1.entities.User;
import org.springframework.data.domain.Page;

public interface IUsersService {
    Page<User> getPaginatedUsers(int pageNumber, int pageSize, String needle, String sort, String direction, String username);
    User getUserById(String id);
    void storeUserWithCreator(UserStoreRequest request, String creatorUsername);
    void updateUserById(UserUpdateRequest request, String id);
    void toggleUserStatusById(String id);
}
