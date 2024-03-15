package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.Role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

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
    @Column(name = "id")
    private String id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "default_password", nullable = false)
    private Boolean default_password = true;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "last_logged_at")
    private Date lastLoggedAt;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "deleted_at")
    private Date deletedAt;

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToOne(mappedBy="user")
    private RefreshToken refreshToken;

    @OneToOne(mappedBy="user")
    private UserInformation userInformation;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id")
    private User creator;

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<InventoryHistory> createdInventoryHistories = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<Inventory> createdInventories = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<Product> createdProducts = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<User> createdUsers = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<User> createdMedia = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<Address> createdAddress = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="creator")
    private Set<Tag> createdTags = new HashSet<>();

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(mappedBy="viewer")
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
        return "User(id=" + id + ", username=" + username + ", role=" + role.name() + ")";
    }
}
