package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.authentication.AuthenticationResponse;
import com.shop.croquy.v1.dao.backoffice.RefreshTokenRequest;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse refresh(RefreshTokenRequest request);
}
