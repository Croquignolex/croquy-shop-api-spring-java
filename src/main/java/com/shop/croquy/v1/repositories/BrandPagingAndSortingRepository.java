package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Brand;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandPagingAndSortingRepository extends PagingAndSortingRepository<Brand, String> {
    Page<Brand> findAllByNameContainsOrSlugContains(String nameNeedle, String slugNeedle, Pageable pageable);
}
