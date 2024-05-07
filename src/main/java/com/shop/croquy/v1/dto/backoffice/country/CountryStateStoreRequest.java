package com.shop.croquy.v1.dto.backoffice.country;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CountryStateStoreRequest {
    @NotEmpty(message = "Name field is required")
    protected String name;

    protected String description;

    public State toState(Country country, User creator) {
        State state = new State();

        state.setName(name);
        state.setDescription(description);
        state.setCountry(country);
        state.setCreator(creator);

        return state;
    }
}