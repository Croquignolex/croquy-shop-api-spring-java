package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {
    Optional<Country> findFistByName(String name);
    List<Country> findByNameContains(String name);
    Optional<Country> findFistByNameAndIdNot(String name, String id);
}
