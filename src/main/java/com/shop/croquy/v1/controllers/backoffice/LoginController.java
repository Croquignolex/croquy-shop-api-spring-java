package com.shop.croquy.v1.controllers.backoffice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/backoffice")
public class LoginController {

    @PostMapping(path = "/login")
    public String index() {
        return "";
    }
}
