package com.shop.croquy.v1.dto.backoffice.attributeValue;

import com.shop.croquy.v1.entities.AttributeValue;
import com.shop.croquy.v1.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AttributeValueStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Value field is required")
    protected String value;

    protected String description;

    public AttributeValue toAttributeValue(User creator) {
        AttributeValue attributeValue = new AttributeValue();

        attributeValue.setName(name);
        attributeValue.setValue(value);
        attributeValue.setDescription(description);
        attributeValue.setCreator(creator);

        return attributeValue;
    }
}