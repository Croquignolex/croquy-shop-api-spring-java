package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, String> {
    Optional<Attribute> findFistByName(String name);
    Optional<Attribute> findFistByNameAndIdNot(String name, String id);
}
