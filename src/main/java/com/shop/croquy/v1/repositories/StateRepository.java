package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, String> {
    Optional<State> findFistByNameAndCountry(String name, Country country);
    List<State> findByEnabledOrderByName(boolean enabled);
    Optional<State> findFistByNameAndIdNotAndCountry(String name, String id, Country country);
}
