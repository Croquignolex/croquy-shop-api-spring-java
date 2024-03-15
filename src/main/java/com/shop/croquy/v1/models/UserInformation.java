package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.Gender;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_information")
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

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

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email_verified_at")
    private Date emailVerifiedAt;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PreUpdate
    public void updateTrigger() {
        updatedAt = new Date();
    }
}
