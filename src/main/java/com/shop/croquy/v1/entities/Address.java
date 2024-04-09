package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.AddressMorphType;
import com.shop.croquy.v1.enums.AddressType;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AddressType type = AddressType.DEFAULT;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "address_morph_type", nullable = false)
    private AddressMorphType addressMorphType;

    @Column(name = "address_morph_id", nullable = false)
    private String addressMorphId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}

