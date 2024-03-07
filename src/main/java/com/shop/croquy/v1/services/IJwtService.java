package com.shop.croquy.v1.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUsernameFormToken(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
