package com.shop.croquy.v1.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cs_users_information")
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

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        updatedAt = new Date();
    }
}
