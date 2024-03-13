package com.shop.croquy;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.repositories.UserRepository;

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
public class CroquyShopApiSpringJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CroquyShopApiSpringJavaApplication.class, args);
    }

    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }

    @Bean
    public CommandLineRunner run(UserRepository userRepository) throws Exception {
        return (String[] args) -> {
            List<User> users = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                User customer = new User();
                User admin;
                User superAdmin;

                customer.setEmail("customer" + i + "@croquy.com");
                customer.setPassword("customer");
                customer.setFirstName("Customer " + i);
                customer.setRole(Role.ROLE_CUSTOMER);

                if(i > 19) {
                    admin = new User();

                    admin.setUsername("admin" + i);
                    admin.setPassword("admin");
                    admin.setFirstName("Admin " + i);
                    admin.setRole(Role.ROLE_ADMIN);

                    users.add(admin);
                }

                if(i > 25) {
                    superAdmin = new User();

                    superAdmin.setUsername("super" + i);
                    superAdmin.setPassword("super");
                    superAdmin.setFirstName("Super " + i);
                    superAdmin.setRole(Role.ROLE_SUPER_ADMIN);

                    users.add(superAdmin);
                }

                users.add(customer);
            }

            userRepository.saveAll(users);
        };
    }
}
