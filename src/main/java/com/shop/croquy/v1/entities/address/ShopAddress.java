package com.shop.croquy.v1.entities.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.Shop;
import com.shop.croquy.v1.entities.State;
import com.shop.croquy.v1.entities.User;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "shop_addresses")
public class ShopAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "phone_number_one")
    private String phoneNumberOne;

    @Column(name = "phone_number_two")
    private String phoneNumberTwo;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "created_at")
    private Date createdAt = new Date();
}

