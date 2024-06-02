package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.User;

import com.shop.croquy.v1.entities.Vendor;
import com.shop.croquy.v1.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findFistByUsername(String username);
    List<User> findByUsernameContains(String username);
    Optional<User> findByUsernameAndRoleNotIn(String username, Collection<Role> roles);
    Optional<User> findByUsernameAndRefreshTokenAndRoleNotIn(String username, String refreshToken, Collection<Role> roles);
}
