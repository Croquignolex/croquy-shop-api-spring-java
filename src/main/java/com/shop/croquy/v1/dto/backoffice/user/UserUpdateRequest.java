package com.shop.croquy.v1.dto.backoffice.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserUpdateRequest {
    @NotEmpty(message = "Old password field is required")
    protected String oldPassword;

    @NotEmpty(message = "Password field is required")
    protected String password;

    @NotEmpty(message = "Role field is required")
    protected String role;
}