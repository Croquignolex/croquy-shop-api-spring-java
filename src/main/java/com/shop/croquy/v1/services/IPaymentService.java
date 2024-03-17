package com.shop.croquy.v1.services;

import com.shop.croquy.v1.models.Media;

public interface IPaymentService {
    Media getHardCopy(String id);
}
