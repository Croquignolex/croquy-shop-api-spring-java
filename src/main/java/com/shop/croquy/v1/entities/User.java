package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.Gender;
import com.shop.croquy.v1.enums.Role;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profession")
    private String profession;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.UNKNOWN;

    @Column(name = "is_first_purchase", nullable = false)
    private Boolean firstPurchase = false;

    @Column(name = "default_password", nullable = false)
    private Boolean default_password = true;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "last_logged_at")
    private Date lastLoggedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email_verified_at")
    private Date emailVerifiedAt;

    /*@OneToMany(mappedBy = "creator")
    private Set<Country> createdCountries = new HashSet<>();*/
//
//    @OneToMany(mappedBy = "creator")
//    private Set<InventoryHistory> createdInventoryHistories = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Inventory> createdInventories = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Product> createdProducts = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<User> createdUsers = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<User> createdMedia = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Address> createdAddress = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Tag> createdTags = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Brand> createdBrands = new HashSet<>();
//
//    @OneToMany(mappedBy = "creator")
//    private Set<Vendor> createdVendors = new HashSet<>();
//
//    @OneToMany(mappedBy = "viewer")
//    private Set<View> views = new HashSet<>();
//
//    @OneToMany(mappedBy = "rater")
//    private Set<Rating> rates = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @PrePersist
    public void createTrigger() {

        password = new BCryptPasswordEncoder().encode(this.password);

        if(role.equals(Role.CUSTOMER)) {
            username = email;
        }
    }
}
