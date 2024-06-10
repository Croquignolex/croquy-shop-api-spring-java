package com.shop.croquy.v1.dto.backoffice.authentication;

import com.shop.croquy.v1.entities.media.UserAvatar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {
    private String username;
    private String firstName;
    private String accessToken;
    private String refreshToken;
    private String role;
    private UserAvatar avatar;
}