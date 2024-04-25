package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface StatePagingAndSortingRepository extends PagingAndSortingRepository<State, String> {
    Page<State> findAllByNameContainsOrCountryIsInOrCreatorIsIn(String nameNeedle, Collection<Country> country, Collection<User> creator, Pageable pageable);
    Page<State> findAllByNameContainsOrCreatorIsInAndCountryId(String nameNeedle, Collection<User> creator, String countryId, Pageable pageable);
    Page<State> findAllByCountryId(String countryId, Pageable pageable);
}
