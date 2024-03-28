package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.entities.Address;
import com.shop.croquy.v1.entities.Media;

public interface IVendorService {
    Media getLogoById(String id);
    Address getAddressById(String id);
//    List<Tag> getTagsById(String id);
}
