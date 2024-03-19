package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.Address;
import com.shop.croquy.v1.models.Tag;

import java.util.List;

public interface IShopService {
    Address getAddressById(String id);
//    List<Tag> getTagsById(String id);
}
