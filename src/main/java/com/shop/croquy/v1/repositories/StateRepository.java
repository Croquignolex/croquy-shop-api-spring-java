package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, String> {
    Optional<State> findFistByName(String name);
    Optional<State> findFistByNameAndIdNot(String name, String id);
}
