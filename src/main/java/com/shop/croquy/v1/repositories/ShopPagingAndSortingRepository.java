package com.shop.croquy.v1.repositories;
 
import com.shop.croquy.v1.entities.Shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPagingAndSortingRepository extends PagingAndSortingRepository<Shop, String> {
    Page<Shop> findAllByNameContainsOrSlugContains(String nameNeedle, String slugNeedle, Pageable pageable);
}
