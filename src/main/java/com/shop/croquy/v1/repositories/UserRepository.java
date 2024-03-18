package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndRoleNotIn(String username, Collection<Role> roles);
    Optional<User> findByUsernameAndRoleIn(String username, Collection<Role> roles);
}
