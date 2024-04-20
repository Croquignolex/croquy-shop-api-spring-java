package com.shop.croquy.v1.dto.backoffice.country;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CountryStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    protected String phoneCode;

    protected String description;

    public Country toCountry(User creator) {
        Country country = new Country();

        country.setName(name);
        country.setPhoneCode(phoneCode);
        country.setDescription(description);
        country.setCreator(creator);

        return country;
    }
}