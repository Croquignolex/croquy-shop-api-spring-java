package com.shop.croquy.v1.dao.backoffice.shop;

import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.models.User;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShopStoreRequest {
    @NotEmpty(message = "Name field is required")
    private String name;

    @NotEmpty(message = "Slug field is required")
    private String slug;

    private String description;

    public Shop toShop(User creator) {
        Shop shop = new Shop();

        shop.setName(name);
        shop.setSlug(slug);
        shop.setDescription(description);
        shop.setCreator(creator);

        return shop;
    }
}