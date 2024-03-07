package com.shop.croquy.v1.controllers.backoffice;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/backoffice/home")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("home response");
    }
}
