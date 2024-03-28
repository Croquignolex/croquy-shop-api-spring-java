package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.dto.backoffice.shop.ShopUpdateRequest;
import com.shop.croquy.v1.entities.Shop;

import org.springframework.data.domain.Page;

public interface IShopsService {
    Page<Shop> getPaginatedShops(int pageNumber, int pageSize, String needle);
    void store(ShopStoreRequest request, String userUsername);
    void update(ShopUpdateRequest request, String id);
    void destroyById(String id);
}
