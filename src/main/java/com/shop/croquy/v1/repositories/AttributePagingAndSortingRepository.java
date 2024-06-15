package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributePagingAndSortingRepository extends PagingAndSortingRepository<Attribute, String> {
    Page<Attribute> findAllByNameContains(String nameNeedle, Pageable pageable);
}
