package com.shop.croquy.v1.dto.backoffice.vendor;

import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.entities.Vendor;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VendorStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    protected String description;

    public Vendor toVendor(User creator) {
        Vendor vendor = new Vendor();

        vendor.setName(name);
        vendor.setDescription(description);
        vendor.setCreator(creator);

        return vendor;
    }
}