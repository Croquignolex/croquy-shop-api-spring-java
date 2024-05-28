package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attributeValue.AttributeValueUpdateRequest;
import com.shop.croquy.v1.entities.AttributeValue;
import org.springframework.data.domain.Page;

public interface IAttributeValuesService {
    Page<AttributeValue> getPaginatedAttributeValues(int pageNumber, int pageSize, String needle);
    AttributeValue getAttributeValueById(String id);
    void storeAttributeValueWithCreator(AttributeValueStoreRequest request, String creatorUsername);
    void updateAttributeValueById(AttributeValueUpdateRequest request, String id);
    void toggleAttributeValueStatusById(String id);
    void destroyAttributeValueById(String id);   
}
