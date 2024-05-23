package com.shop.croquy.v1.dto.backoffice.category;

import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.User;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String seoTitle;

    protected String seoDescription;

    protected String description;

    @NotEmpty(message = "GroupId field is required")
    protected String groupId;

    public Category toCategory(Group group, User creator) {
        Category category = new Category();

        category.setName(name);
        category.setSlug(slug);
        category.setSeoTitle(seoTitle);
        category.setSeoDescription(seoDescription);
        category.setDescription(description);
        category.setGroup(group);
        category.setCreator(creator);

        return category;
    }
}