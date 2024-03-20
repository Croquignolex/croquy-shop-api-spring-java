package com.shop.croquy;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.repositories.ShopRepository;
import com.shop.croquy.v1.repositories.UserRepository;

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
            List<User> users = new ArrayList<>();
            List<Shop> shops = new ArrayList<>();

            users.add(seedUsers(100, Role.SUPER_ADMIN));
            users.add(seedUsers(101, Role.CUSTOMER));

            for (int i = 0; i < 20; i++) {
                shops.add(seedShops(i));
            }

            userRepository.saveAll(users);
            shopRepository.saveAll(shops);
        };
    }

    private User seedUsers(int i, Role role) {
        User user = new User();
        user.setEmail("user-" + i + "@croquy-shop.com");
        user.setFirstName("User " + i);
        user.setUsername("user" + i);
        user.setPassword("user");
        user.setRole(role);
        return user;
    }

    private Shop seedShops(int i) {
        Shop shop = new Shop();
        shop.setName("Shop " + i);
        shop.setSlug("shop-" + i);
        return shop;
    }
}
