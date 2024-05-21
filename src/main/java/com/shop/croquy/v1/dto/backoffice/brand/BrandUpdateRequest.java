package com.shop.croquy.v1.dto.backoffice.brand;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BrandUpdateRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String website;

    protected String seoTitle;

    protected String seoDescription;

    protected String description;
}