package com.shop.croquy.v1.dto.backoffice.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequest {
    @NotEmpty(message = "Token field is required")
    private String token;

    @JsonCreator
    public RefreshTokenRequest(String token) {
        this.token = token;
    }
}