package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByUsernameContains(String username);
    Optional<User> findByUsernameAndRefreshToken(String username, String refreshToken);
}
