package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.models.Shop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {
}
