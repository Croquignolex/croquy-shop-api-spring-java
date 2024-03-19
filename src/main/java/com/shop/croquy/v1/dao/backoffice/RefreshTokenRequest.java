package com.shop.croquy.v1.dao.backoffice;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RefreshTokenRequest {
    @NotEmpty(message = "Token field is required")
    private String token;
}