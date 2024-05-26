package com.shop.croquy.v1.dto.backoffice.attributeValue;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AttributeValueUpdateRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Value field is required")
    protected String value;

    protected String description;
}