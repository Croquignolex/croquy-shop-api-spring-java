package com.shop.croquy.v1.dto.backoffice.group;

import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.User;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GroupStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String seoTitle;

    protected String seoDescription;

    protected String description;

    public Group toGroup(User creator) {
        Group group = new Group();

        group.setName(name);
        group.setSlug(slug);
        group.setSeoTitle(seoTitle);
        group.setSeoDescription(seoDescription);
        group.setDescription(description);
        group.setCreator(creator);

        return group;
    }
}