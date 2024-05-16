package com.shop.croquy.v1.controllers.web;

import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.services.backoffice.CountriesService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/select")
public class SelectListController {
    private final CountriesService countriesService;

//    @GetMapping(path = "countries")
    public ResponseEntity<List<Country>> index() {
        List<Country> enabledCountries = countriesService.getAllEnabledCountriesOrderByName();

        return ResponseEntity.status(HttpStatus.OK).body(enabledCountries);
    }
}
