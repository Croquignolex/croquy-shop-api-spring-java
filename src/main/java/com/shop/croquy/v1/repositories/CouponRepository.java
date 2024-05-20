package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    Optional<Coupon> findFistByCode(String name);
    Optional<Coupon> findFistByCodeAndIdNot(String name, String id);
}
