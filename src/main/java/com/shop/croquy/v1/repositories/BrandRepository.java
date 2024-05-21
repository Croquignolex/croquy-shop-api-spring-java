package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Optional<Brand> findFistByName(String name);
    Optional<Brand> findFistBySlug(String slug);
    List<Brand> findByNameContains(String name);
    List<Brand> findByEnabledOrderByName(boolean enabled);
    Optional<Brand> findFistByNameAndIdNot(String name, String id);
    Optional<Brand> findFistBySlugAndIdNot(String slug, String id);
}
