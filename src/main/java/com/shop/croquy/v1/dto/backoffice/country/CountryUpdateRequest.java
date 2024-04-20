package com.shop.croquy.v1.dto.backoffice.country;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CountryUpdateRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    protected String phoneCode;

    protected String description;
}