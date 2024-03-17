package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dao.backoffice.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.AuthenticationResponse;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.RefreshToken;
import com.shop.croquy.v1.repositories.RefreshTokenRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final String username = request.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        Collection<Role> excludedRoles = new ArrayList<>();
        excludedRoles.add(Role.ROLE_CUSTOMER);
        var user = userRepository.findByUsernameAndRoleNotIn(username, excludedRoles).orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        String accessToken = jwtService.generateToken(user, false);
        String refreshToken = jwtService.generateToken(user, true);

        if(user.getRefreshToken() == null) {
            RefreshToken refreshTokenData = new RefreshToken();
            refreshTokenData.setToken(refreshToken);
            user.setRefreshToken(refreshTokenData);
            userRepository.save(user);
        } else {
            refreshToken = user.getRefreshToken().getToken();
        }

        log.info("Authentication successful ===> " + user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .build();
    }
}