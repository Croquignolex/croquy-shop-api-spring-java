package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationResponse;
import com.shop.croquy.v1.dao.backoffice.RefreshTokenRequest;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.JwtService;
import com.shop.croquy.v1.services.UserService;
import com.shop.croquy.v1.services.interfaces.IAuthenticationService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final String username = request.getUsername();

        try {
            // Authenticate
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            var user = userRepository.findByUsernameAndRoleNotIn(username, List.of(Role.CUSTOMER))
                    .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

            String accessToken = jwtService.generateToken(user, false);
            String refreshToken = jwtService.generateToken(user, true);

            if(user.getRefreshToken() == null) {
                user.setRefreshToken(refreshToken);
                userRepository.save(user);
            } else {
                refreshToken = user.getRefreshToken();
            }

            log.info("################################# [Login successful (username)] ===> " + username);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .role(user.getRole().name())
                    .username(user.getUsername())
                    .firstName(user.getFirstName())
                    .build();
        } catch (Exception e) {
            log.warn("################################# [Authentication failed] ===> " + e.getMessage());

            return null;
        }
    }

    @Override
    public AuthenticationResponse refresh(RefreshTokenRequest request) {
        final String jwtRefreshToken = request.getToken();

        try {
            String username = jwtService.extractUsernameFormToken(jwtRefreshToken);

            if (StringUtils.isNotEmpty(username)) {
                UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

                if (jwtService.isTokenValid(jwtRefreshToken, userDetails, true)) {
                    var user = userRepository.findByUsernameAndRefreshTokenAndRoleNotIn(username, jwtRefreshToken, List.of(Role.CUSTOMER))
                            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

                    String accessToken = jwtService.generateToken(user, false);

                    log.info("################################# [Refresh token successful (username)] ===> " + username);

                    return AuthenticationResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(jwtRefreshToken)
                            .role(user.getRole().name())
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .build();
                }
            }
        } catch (Exception e) {
            log.warn("################################# [Refresh token failed] ===> " + e.getMessage());
        }

        return null;
    }
}