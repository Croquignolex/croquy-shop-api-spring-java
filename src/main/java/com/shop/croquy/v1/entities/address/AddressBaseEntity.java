package com.shop.croquy.v1.entities.address;

import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class AddressBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    protected String id;

    @Column(name = "street_address", nullable = false)
    protected String streetAddress;

    @Column(name = "zipcode")
    protected String zipcode;

    @Column(name = "phone_number_one")
    protected String phoneNumberOne;

    @Column(name = "phone_number_two")
    protected String phoneNumberTwo;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    protected String description = "";

    @ManyToOne
    @JoinColumn(name = "state_id")
    protected State state;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    protected User creator;

    @Column(name = "created_at")
    protected Date createdAt = new Date();
}

