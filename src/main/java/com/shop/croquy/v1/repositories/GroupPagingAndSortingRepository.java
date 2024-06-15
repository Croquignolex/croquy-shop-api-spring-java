package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPagingAndSortingRepository extends PagingAndSortingRepository<Group, String> {
    Page<Group> findAllByNameContainsOrSlugContains(String nameNeedle, String slugNeedle, Pageable pageable);
}
