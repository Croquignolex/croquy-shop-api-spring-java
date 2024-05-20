package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Coupon;
import com.shop.croquy.v1.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CouponPagingAndSortingRepository extends PagingAndSortingRepository<Coupon, String> {
    Page<Coupon> findAllByCodeContainsOrDiscountOrCreatorIsIn(String code, Integer discount, Collection<User> creator, Pageable pageable);
}
