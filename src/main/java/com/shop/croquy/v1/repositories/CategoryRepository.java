package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findFistByNameAndGroup(String name, Group group);
    Optional<Category> findFistBySlugAndGroup(String slug, Group group);
    List<Category> findByEnabledOrderByName(boolean enabled);
    Optional<Category> findFistByNameAndIdNotAndGroup(String name, String id, Group group);
    Optional<Category> findFistBySlugAndIdNotAndGroup(String name, String id, Group group);
}
