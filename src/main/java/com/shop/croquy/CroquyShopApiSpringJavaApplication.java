package com.shop.croquy;

import com.shop.croquy.v1.entities.*;
import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.repositories.CountryRepository;
import com.shop.croquy.v1.repositories.StateRepository;
import com.shop.croquy.v1.repositories.BrandRepository;
import com.shop.croquy.v1.repositories.ShopRepository;
import com.shop.croquy.v1.repositories.GroupRepository;
import com.shop.croquy.v1.repositories.CategoryRepository;
import com.shop.croquy.v1.repositories.VendorRepository;
import com.shop.croquy.v1.repositories.AttributeRepository;
import com.shop.croquy.v1.repositories.AttributeValueRepository;

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
    private final GroupRepository groupRepository;
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;

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

                User user = userRepository.save(seedUsers(100, Role.SUPER_ADMIN));
                users.add(seedUsers(101, Role.ADMIN));
                users.add(seedUsers(102, Role.MANAGER));
                users.add(seedUsers(103, Role.SELLER));

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
                brand.setName("Nike");
                brand.setSlug("nike");
                brand.setWebsite("info@nike.com");
                brand.setSeoTitle("Nike");
                brand.setSeoDescription("This seo description is a well description for this brand. Make sur you figure this well");
                brand.setCreator(user);
                brand.setDescription("This description is a well description for this brand. Make sur you figure this well");

                Shop shop = new Shop();
                shop.setName("Siège");
                shop.setSlug("siege");
                shop.setCreator(user);
                shop.setDescription("This description is a well description for this shop. Make sur you figure this well");

                Group group = new Group();
                group.setName("Appliances");
                group.setSlug("appliances");
                group.setSeoTitle("Appliances");
                group.setSeoDescription("This seo description is a well description for this group. Make sur you figure this well");
                group.setCreator(user);
                group.setDescription("This description is a well description for this group. Make sur you figure this well");

                Category category = new Category();
                category.setName("Kitchen");
                category.setSlug("kitchen");
                category.setSeoTitle("Kitchen");
                category.setSeoDescription("This seo description is a well description for this category. Make sur you figure this well");
                category.setCreator(user);
                category.setGroup(group);
                category.setDescription("This description is a well description for this category. Make sur you figure this well");

                Vendor vendor = new Vendor();
                vendor.setName("Wise computer");
                vendor.setCreator(user);
                vendor.setDescription("This description is a well description for this vendor. Make sur you figure this well");

                Attribute attribute = new Attribute();
                attribute.setName("Shape");
                attribute.setCreator(user);
                attribute.setDescription("This description is a well description for this attribute. Make sur you figure this well");

                AttributeValue attributeValue = new AttributeValue();
                attributeValue.setName("Round");
                attributeValue.setValue("Round");
                attributeValue.setCreator(user);
                attributeValue.setDescription("This description is a well description for this attribute value. Make sur you figure this well");

                for(int i = 0; i < 25; i++){
                    users.add(seedUsers(i + 104, Role.CUSTOMER));
                }

                userRepository.saveAll(users);
                countryRepository.save(country);
                stateRepository.save(state);
                brandRepository.save(brand);
                shopRepository.save(shop);
                groupRepository.save(group);
                categoryRepository.save(category);
                vendorRepository.save(vendor);
                attributeRepository.save(attribute);
                attributeValueRepository.save(attributeValue);
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
