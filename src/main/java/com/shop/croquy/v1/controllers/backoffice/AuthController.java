package com.shop.croquy.v1.controllers.backoffice;

import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationResponse;
import com.shop.croquy.v1.dao.backoffice.RefreshTokenRequest;
import com.shop.croquy.v1.services.backoffice.AuthenticationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/backoffice/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse loginResponse = authenticationService.login(request);

        HttpStatus httpStatus = (loginResponse != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(loginResponse);
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthenticationResponse refreshResponse = authenticationService.refresh(request);

        HttpStatus httpStatus = (refreshResponse != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(httpStatus).body(refreshResponse);
    }
}
