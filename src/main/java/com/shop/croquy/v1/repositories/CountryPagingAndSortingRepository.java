package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountryPagingAndSortingRepository extends PagingAndSortingRepository<Country, String> {
    Page<Country> findAllByNameContainsOrPhoneCodeContainsOrCreatorIsIn(String nameNeedle, String phoneCodeNeedle, Collection<User> creator, Pageable pageable);
}
