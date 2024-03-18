package com.shop.croquy.v1.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsernameFormToken(String token);

    String generateToken(UserDetails userDetails, boolean everlasting);

    boolean isTokenValid(String token, UserDetails userDetails, boolean everlasting);
}
