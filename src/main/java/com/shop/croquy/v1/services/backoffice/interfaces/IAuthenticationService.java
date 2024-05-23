package com.shop.croquy.v1.services.backoffice.interfaces;

import com.shop.croquy.v1.dto.backoffice.authentication.AuthenticationRequest;
import com.shop.croquy.v1.dto.backoffice.authentication.AuthenticationResponse;
import com.shop.croquy.v1.dto.backoffice.authentication.RefreshTokenRequest;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse refresh(RefreshTokenRequest request);
}
