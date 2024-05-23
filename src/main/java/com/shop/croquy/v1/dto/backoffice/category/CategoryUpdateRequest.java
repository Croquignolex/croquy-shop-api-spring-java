package com.shop.croquy.v1.dto.backoffice.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryUpdateRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String seoTitle;

    protected String seoDescription;

    protected String description;

    @NotEmpty(message = "GroupId field is required")
    protected String groupId;
}