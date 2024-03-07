package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dao.backoffice.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.AuthenticationResponse;
import com.shop.croquy.v1.repositories.UserRepository;

import com.shop.croquy.v1.services.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final String username = request.getUsername();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

        var user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwt).username(user.getUsername()).build();
    }
}