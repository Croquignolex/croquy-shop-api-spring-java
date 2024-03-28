package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.entities.Address;

public interface IShopService {
    Address getAddressById(String id);
//    List<Tag> getTagsById(String id);
}
