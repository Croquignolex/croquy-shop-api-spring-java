package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.attribute.AttributeStoreRequest;
import com.shop.croquy.v1.dto.backoffice.attribute.AttributeUpdateRequest;
import com.shop.croquy.v1.entities.Attribute;
import org.springframework.data.domain.Page;

public interface IAttributesService {
    Page<Attribute> getPaginatedAttributes(int pageNumber, int pageSize, String needle, String sort, String direction);
    Attribute getAttributeById(String id);
    void storeAttributeWithCreator(AttributeStoreRequest request, String creatorUsername);
    void updateAttributeById(AttributeUpdateRequest request, String id);
    void toggleAttributeStatusById(String id);
    void destroyAttributeById(String id);
}
