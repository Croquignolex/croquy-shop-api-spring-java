package com.shop.croquy;

import com.shop.croquy.v1.entities.*;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.repositories.CountryRepository;
import com.shop.croquy.v1.repositories.StateRepository;
import com.shop.croquy.v1.repositories.BrandRepository;
import com.shop.croquy.v1.repositories.ShopRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class CroquyShopApiSpringJavaApplication {
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final BrandRepository brandRepository;
    private final ShopRepository shopRepository;

    public static void main(String[] args) {
        SpringApplication.run(CroquyShopApiSpringJavaApplication.class, args);
    }

    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }

    @Bean
    public CommandLineRunner run() {
        return (String[] args) -> {
            if(userRepository.findByUsername("user100").isEmpty()) {
                List<User> users = new ArrayList<>();
                List<Shop> shops = new ArrayList<>();

                User user = userRepository.save(seedUsers(200, Role.CUSTOMER));

                Country country = new Country();
                country.setName("Cameroun");
                country.setPhoneCode("237");
                country.setCreator(user);
                country.setDescription("This description is a well description for this country. Make sur you figure this well");

                State state = new State();
                state.setName("Douala");
                state.setCountry(country);
                state.setCreator(user);
                state.setDescription("This description is a well description for this state. Make sur you figure this well");

                Brand brand = new Brand();
                brand.setName("Brand");
                brand.setSlug("brand");
                brand.setWebsite("brand@exemple.com");
                brand.setSeoTitle("brand");
                brand.setSeoDescription("This seo description is a well description for this brand. Make sur you figure this well");
                brand.setCreator(user);
                brand.setDescription("This description is a well description for this brand. Make sur you figure this well");

                for(int i = 0; i < 25; i++){
                    Shop shop = new Shop();
                    shop.setName("Brand" + i);
                    shop.setSlug("brand" + i);
                    shop.setCreator(user);
                    shop.setDescription("This description is a well description for this brand. Make sur you figure this well" + i);
                    shops.add(shop);
                }

                users.add(seedUsers(100, Role.SUPER_ADMIN));
                users.add(seedUsers(101, Role.CUSTOMER));

                userRepository.saveAll(users);
                countryRepository.save(country);
                stateRepository.save(state);
                brandRepository.save(brand);
                shopRepository.saveAll(shops);
            }
        };
    }

    private User seedUsers(int i, Role role) {
        User user = new User();
        user.setEmail("user" + i + "@croquy-shop.com");
        user.setFirstName("User " + i);
        user.setUsername("user" + i);
        user.setPassword("user");
        user.setRole(role);
        return user;
    }
}
