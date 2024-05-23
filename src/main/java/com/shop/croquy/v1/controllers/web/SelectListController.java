package com.shop.croquy.v1.controllers.web;

import com.shop.croquy.v1.entities.Category;
import com.shop.croquy.v1.entities.Country;
import com.shop.croquy.v1.entities.Group;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.services.backoffice.CategoriesService;
import com.shop.croquy.v1.services.backoffice.CountriesService;
import com.shop.croquy.v1.services.backoffice.GroupsService;
import com.shop.croquy.v1.services.backoffice.StatesService;

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
    private final GroupsService groupsService;
    private final StatesService statesService;
    private final CategoriesService categoriesService;

    @GetMapping(path = "countries")
    public ResponseEntity<List<Country>> countries() {
        List<Country> enabledCountries = countriesService.getAllEnabledCountriesOrderByName();

        return ResponseEntity.status(HttpStatus.OK).body(enabledCountries);
    }

    @GetMapping(path = "groups")
    public ResponseEntity<List<Group>> groups() {
        List<Group> enabledGroups = groupsService.getAllEnabledGroupsOrderByName();

        return ResponseEntity.status(HttpStatus.OK).body(enabledGroups);
    }

    @GetMapping(path = "categories")
    public ResponseEntity<List<Category>> categories() {
        List<Category> enabledCategories = categoriesService.getAllEnabledCategoriesOrderByName();

        return ResponseEntity.status(HttpStatus.OK).body(enabledCategories);
    }

    @GetMapping(path = "states")
    public ResponseEntity<List<State>> states() {
        List<State> enabledStates = statesService.getAllEnabledStatesOrderByName();

        return ResponseEntity.status(HttpStatus.OK).body(enabledStates);
    }
}
