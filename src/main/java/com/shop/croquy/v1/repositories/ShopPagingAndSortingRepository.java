package com.shop.croquy.v1.repositories;
 
import com.shop.croquy.v1.entities.Shop;

import com.shop.croquy.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ShopPagingAndSortingRepository extends PagingAndSortingRepository<Shop, String> {
    Page<Shop> findAllByNameContainsOrSlugContainsOrCreatorIsIn(String nameNeedle, String slugNeedle, Collection<User> creator, Pageable pageable);
}
