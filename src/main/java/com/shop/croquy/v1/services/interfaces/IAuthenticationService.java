package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.dao.backoffice.AuthenticationRequest;
import com.shop.croquy.v1.dao.backoffice.AuthenticationResponse;

public interface IAuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
}
