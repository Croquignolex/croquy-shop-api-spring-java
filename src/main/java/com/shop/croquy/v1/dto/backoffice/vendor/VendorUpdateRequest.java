package com.shop.croquy.v1.dto.backoffice.vendor;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VendorUpdateRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    protected String description;
}