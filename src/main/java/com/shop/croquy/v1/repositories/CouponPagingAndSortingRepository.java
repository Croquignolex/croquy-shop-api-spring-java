package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Coupon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponPagingAndSortingRepository extends PagingAndSortingRepository<Coupon, String> {
    Page<Coupon> findAllByCodeContainsOrDiscount(String code, Integer discount, Pageable pageable);
}
