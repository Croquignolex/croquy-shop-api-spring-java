package com.shop.croquy;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.models.UserInformation;
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

//    @Bean
    public CommandLineRunner run(UserRepository userRepository) throws Exception {
        return (String[] args) -> {
            List<User> users = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                User customer = new User();
                User admin;
                User superAdmin;

                UserInformation customerInformation = new UserInformation();

                customerInformation.setFirstName("Customer " + i);
                customer.setUserInformation(customerInformation);
                customer.setEmail("customer" + i + "@croquy.com");
                customer.setPassword("customer");
                customer.setRole(Role.ROLE_CUSTOMER);

                users.add(customer);

                if(i > 19) {
                    admin = new User();

                    UserInformation adminInformation = new UserInformation();

                    adminInformation.setFirstName("Admin " + i);
                    admin.setUserInformation(adminInformation);
                    admin.setUsername("admin" + i);
                    admin.setPassword("admin");
                    admin.setRole(Role.ROLE_ADMIN);

                    users.add(admin);
                }

                if(i > 25) {
                    superAdmin = new User();

                    UserInformation superAdminInformation = new UserInformation();

                    superAdminInformation.setFirstName("Super " + i);
                    superAdmin.setUserInformation(superAdminInformation);
                    superAdmin.setUsername("super" + i);
                    superAdmin.setPassword("super");
                    superAdmin.setRole(Role.ROLE_SUPER_ADMIN);

                    users.add(superAdmin);
                }

                users.add(customer);
            }

            userRepository.saveAll(users);
        };
    }
}
