package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    Optional<Group> findFistByName(String name);
    Optional<Group> findFistBySlug(String slug);
    List<Group> findByEnabledOrderByName(boolean enabled);
    Optional<Group> findFistByNameAndIdNot(String name, String id);
    Optional<Group> findFistBySlugAndIdNot(String slug, String id);
}
