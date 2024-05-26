package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Attribute;
import com.shop.croquy.v1.entities.Brand;
import com.shop.croquy.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AttributePagingAndSortingRepository extends PagingAndSortingRepository<Attribute, String> {
    Page<Attribute> findAllByNameContainsOrTypeContainsOrCreatorIsIn(String nameNeedle, String typeNeedle, Collection<User> creator, Pageable pageable);
}
