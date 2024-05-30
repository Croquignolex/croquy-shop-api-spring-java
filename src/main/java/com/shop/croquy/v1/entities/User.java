package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.croquy.v1.entities.address.UserAddress;
import com.shop.croquy.v1.entities.media.UserAvatar;
import com.shop.croquy.v1.enums.Gender;
import com.shop.croquy.v1.enums.Role;

import jakarta.persistence.*;

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
@Table(name = "cs_users")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profession")
    private String profession;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.UNKNOWN;

    @JsonIgnore
    @Column(name = "is_first_purchase", nullable = false)
    private Boolean firstPurchase = false;

    @JsonIgnore
    @Column(name = "default_password", nullable = false)
    private Boolean default_password = true;

    @JsonIgnore
    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "last_logged_at")
    private Date lastLoggedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "birthdate")
    private Date birthdate;

    @JsonIgnore
    @Column(name = "email_verified_at")
    private Date emailVerifiedAt;

    @OneToOne(mappedBy = "user")
    private UserAvatar avatar;

    @OneToOne(mappedBy = "user")
    private UserAddress defaultAddress;

    @OneToOne(mappedBy = "user")
    private UserAddress billingAddress;

    @OneToOne(mappedBy = "user")
    private UserAddress shippingAddress;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
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

    @JsonIgnore
    public boolean isNonDeletable() { 
        return true;
    }
}
