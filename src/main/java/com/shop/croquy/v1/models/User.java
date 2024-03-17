package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.Gender;
import com.shop.croquy.v1.enums.Role;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

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

    @Column(name = "description")
    private String description;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.UNKNOWN;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_first_purchase", nullable = false)
    private Boolean firstPurchase = false;

    @Column(name = "default_password", nullable = false)
    private Boolean default_password = true;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "last_logged_at")
    private Date lastLoggedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email_verified_at")
    private Date emailVerifiedAt;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<InventoryHistory> createdInventoryHistories = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Inventory> createdInventories = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Product> createdProducts = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<User> createdUsers = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<User> createdMedia = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Address> createdAddress = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Tag> createdTags = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Brand> createdBrands = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.DETACH)
    private Set<Vendor> createdVendors = new HashSet<>();

    @OneToMany(mappedBy = "viewer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<View> views = new HashSet<>();

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
    }

    @PreUpdate
    public void updateTrigger() {
        updatedAt = new Date();
    }

    @Override
    public String toString() {
        return "User(id = " + id + ", username = " + username + ", role = " + role.name() + ")";
    }
}
