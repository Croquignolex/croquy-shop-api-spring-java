package com.shop.croquy.v1.dto.backoffice.attribute;

import com.shop.croquy.v1.entities.Attribute;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.enums.AttributeType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AttributeStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Type field is required")
    protected AttributeType type;

    protected String description;

    public Attribute toAttribute(User creator) {
        Attribute attribute = new Attribute();

        attribute.setName(name);
        attribute.setType(type);
        attribute.setDescription(description);
        attribute.setCreator(creator);

        return attribute;
    }
}