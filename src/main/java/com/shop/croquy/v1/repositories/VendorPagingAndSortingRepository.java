package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Vendor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorPagingAndSortingRepository extends PagingAndSortingRepository<Vendor, String> {
    Page<Vendor> findAllByNameContains(String nameNeedle, Pageable pageable);
}
