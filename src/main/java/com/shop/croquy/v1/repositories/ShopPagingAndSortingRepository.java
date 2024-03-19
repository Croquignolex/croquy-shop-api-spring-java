package com.shop.croquy.v1.repositories;
 
import com.shop.croquy.v1.models.Shop;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopPagingAndSortingRepository extends PagingAndSortingRepository<Shop, String> {
    Page<Shop> findAll();
}
