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
            User customer = new User();
            User admin = new User();
            User superAdmin = new User();

            customer.setEmail("customer@croquy.com");
            customer.setPassword("customer");
            customer.setFirstName("Customer");
            customer.setRole(Role.ROLE_CUSTOMER);

            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setFirstName("Admin");
            admin.setRole(Role.ROLE_ADMIN);

            superAdmin.setUsername("super");
            superAdmin.setPassword("super");
            superAdmin.setFirstName("Super");
            superAdmin.setRole(Role.ROLE_SUPER_ADMIN);

            userRepository.save(customer);
            userRepository.save(admin);
            userRepository.save(superAdmin);
            userRepository.findAll().forEach(System.out::println);
        };
    }
}
