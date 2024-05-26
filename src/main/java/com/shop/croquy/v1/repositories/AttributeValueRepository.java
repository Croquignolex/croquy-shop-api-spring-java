package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, String> {
    Optional<AttributeValue> findFistByName(String name);
    Optional<AttributeValue> findFistByNameAndIdNot(String name, String id);
}
