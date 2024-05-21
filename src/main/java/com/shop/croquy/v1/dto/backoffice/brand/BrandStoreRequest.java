package com.shop.croquy.v1.dto.backoffice.brand;

import com.shop.croquy.v1.entities.Brand;
import com.shop.croquy.v1.entities.User;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BrandStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String website;

    protected String seoTitle;

    protected String seoDescription;

    protected String description;

    public Brand toBrand(User creator) {
        Brand brand = new Brand();

        brand.setName(name);
        brand.setSlug(slug);
        brand.setWebsite(website);
        brand.setSeoTitle(seoTitle);
        brand.setSeoDescription(seoDescription);
        brand.setDescription(description);
        brand.setCreator(creator);

        return brand;
    }
}