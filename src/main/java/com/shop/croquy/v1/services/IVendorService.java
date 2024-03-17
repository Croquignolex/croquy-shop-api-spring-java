package com.shop.croquy.v1.services;

import com.shop.croquy.v1.models.Address;
import com.shop.croquy.v1.models.Media;

public interface IVendorService {
    Media getLogo(String id);
    Address getAddress(String id);
}
