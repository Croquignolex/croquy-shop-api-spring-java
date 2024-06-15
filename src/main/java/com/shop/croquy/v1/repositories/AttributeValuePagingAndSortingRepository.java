package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.AttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttributeValuePagingAndSortingRepository extends PagingAndSortingRepository<AttributeValue, String> {
    Page<AttributeValue> findAllByNameContainsOrValueContains(String nameNeedle, String valueNeedle, Pageable pageable);
}
