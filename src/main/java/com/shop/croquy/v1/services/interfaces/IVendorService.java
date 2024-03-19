package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.Address;
import com.shop.croquy.v1.models.Media;
import com.shop.croquy.v1.models.Tag;

import java.util.List;

public interface IVendorService {
    Media getLogoById(String id);
    Address getAddressById(String id);
//    List<Tag> getTagsById(String id);
}
