package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.authentication.AuthenticationRequest;
import com.shop.croquy.v1.dto.backoffice.authentication.AuthenticationResponse;
import com.shop.croquy.v1.dto.backoffice.authentication.RefreshTokenRequest;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.JwtService;
import com.shop.croquy.v1.services.UserService;
import com.shop.croquy.v1.services.backoffice.interfaces.IAuthenticationService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.*;

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

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(USER_USERNAME_NOT_FOUND));

        String accessToken = jwtService.generateToken(user, false);
        String refreshToken = jwtService.generateToken(user, true);

        if(user.getRefreshToken() == null) {
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        } else {
            refreshToken = user.getRefreshToken();
        }

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(user.getRole().name())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .build();
    }

    @Override
    public AuthenticationResponse refresh(RefreshTokenRequest request) {
        final String jwtRefreshToken = request.getToken();

        String username = jwtService.extractUsernameFormToken(jwtRefreshToken);

        if (StringUtils.isNotEmpty(username)) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);

            if (jwtService.isTokenValid(jwtRefreshToken, userDetails, true)) {
                var user = userRepository.findByUsernameAndRefreshToken(username, jwtRefreshToken)
                        .orElseThrow(() -> new IllegalArgumentException(CUSTOMER_NOT_AUTHORIZED));

                String accessToken = jwtService.generateToken(user, false);

                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(jwtRefreshToken)
                        .role(user.getRole().name())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .build();
            }
        }

        return null;
    }
}