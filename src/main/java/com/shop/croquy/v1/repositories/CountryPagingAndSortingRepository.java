package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CountryPagingAndSortingRepository extends PagingAndSortingRepository<Country, String> {
    Page<Country> findAllByNameContainsOrPhoneCodeContains(String nameNeedle, String phoneCodeNeedle, Pageable pageable);
}
