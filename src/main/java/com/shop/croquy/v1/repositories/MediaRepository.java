package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.models.Media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    Optional<Media> findByName(String username);
}
