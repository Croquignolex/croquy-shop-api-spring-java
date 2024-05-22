package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shop.croquy.v1.entities.address.VendorAddress;
import com.shop.croquy.v1.entities.media.VendorLogo;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_vendors")
public class Vendor extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @OneToOne(mappedBy = "vendor")
    private VendorLogo logo;

    @OneToOne(mappedBy = "vendor")
    private VendorAddress address;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

//    @OneToMany(mappedBy = "vendor")
//    private Set<Inventory> inventories = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
//        return (long) inventoryHistories.size() > 0 ;
        return false;
    }
}

