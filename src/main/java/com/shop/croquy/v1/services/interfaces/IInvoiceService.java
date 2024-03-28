package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.entities.Media;

public interface IInvoiceService {
    Media getPDFHardCopyById(String id);
    Media getImageHardCopyById(String id);
}
