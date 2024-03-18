package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.AttributeValue;

import java.util.List;

public interface IAttributeService {
    List<AttributeValue> getAttributeValuesByIdAndByProductId(String id, String productId);
}
