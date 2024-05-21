package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.entities.Vendor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface VendorPagingAndSortingRepository extends PagingAndSortingRepository<Vendor, String> {
    Page<Vendor> findAllByNameContainsOrCreatorIsIn(String nameNeedle, Collection<User> creator, Pageable pageable);
}
