package com.shop.croquy.v1.dto.web;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddressUpdateRequest {
    @NotEmpty(message = "Street address field is required")
    private String streetAddress;

    @NotEmpty(message = "Phone number one field is required")
    private String phoneNumberOne;

    private String zipcode;

    private String phoneNumberTwo;

    private String description;

    @NotEmpty(message = "stateId field is required")
    private String stateId;
}