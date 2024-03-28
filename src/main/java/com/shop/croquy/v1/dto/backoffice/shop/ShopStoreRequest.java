package com.shop.croquy.v1.dto.backoffice.shop;

import com.shop.croquy.v1.entities.Shop;
import com.shop.croquy.v1.entities.User;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    @NotEmpty(message = "Slug field is required")
    protected String slug;

    protected String description;

    public Shop toShop(User creator) {
        Shop shop = new Shop();

        shop.setName(name);
        shop.setSlug(slug);
        shop.setDescription(description);
        shop.setCreator(creator);

        return shop;
    }
}