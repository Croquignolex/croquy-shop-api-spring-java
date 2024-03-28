package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
    Optional<Shop> findFistByName(String name);
    Optional<Shop> findFistBySlug(String slug);
    Optional<Shop> findFistByNameAndIdNot(String name, String id);
    Optional<Shop> findFistBySlugAndIdNot(String name, String id);
}
