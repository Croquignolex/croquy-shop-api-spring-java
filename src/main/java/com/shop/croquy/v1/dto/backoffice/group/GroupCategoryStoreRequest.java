package com.shop.croquy.v1.dto.backoffice.group;

import com.shop.croquy.v1.entities.*;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GroupCategoryStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String description;

    protected String seoTitle;

    protected String seoDescription;

    public Category toCategory(Group group, User creator) {
        Category category = new Category();

        category.setName(name);
        category.setSeoTitle(seoTitle);
        category.setSeoDescription(seoDescription);
        category.setDescription(description);
        category.setGroup(group);
        category.setCreator(creator);

        return category;
    }
}