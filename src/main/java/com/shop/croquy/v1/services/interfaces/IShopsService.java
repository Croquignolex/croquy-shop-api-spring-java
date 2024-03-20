package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.Shop;

import org.springframework.data.domain.Page;

public interface IShopsService {
    Page<Shop> getPaginatedShops(int pageNumber, int pageSize);
}
