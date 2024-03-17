package com.shop.croquy.v1.services;

import com.shop.croquy.v1.models.Media;

public interface IInvoiceService {
    Media getHardCopy(String id);
}
