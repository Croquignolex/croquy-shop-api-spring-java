package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dto.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.dto.backoffice.shop.ShopUpdateRequest;
import com.shop.croquy.v1.dto.web.AddressUpdateRequest;
import com.shop.croquy.v1.entities.Shop;
import com.shop.croquy.v1.entities.address.ShopAddress;

import org.springframework.data.domain.Page;

public interface IShopsService {
    Page<Shop> getPaginatedShops(int pageNumber, int pageSize, String needle);
    Shop getShopById(String id);
    void storeShopWithCreator(ShopStoreRequest request, String creatorUsername);
    void updateShopById(ShopUpdateRequest request, String id);
    void destroyShopById(String id);
    void toggleShopStatusById(String id);
    ShopAddress updateShopAddressById(AddressUpdateRequest request, String id, String creatorUsername);
}
