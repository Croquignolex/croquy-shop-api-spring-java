package com.shop.croquy.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.shop.croquy.v1.enums.Role;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cs_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "last_logged_at")
    private Date lastLoggedAt;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonProperty(access = Access.WRITE_ONLY)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_information_id")
    private UserInformation userInformation;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "deleted_at")
    private Date deletedAt;

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
