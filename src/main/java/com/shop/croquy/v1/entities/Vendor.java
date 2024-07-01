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

    @OneToOne(mappedBy = "vendor")
    private VendorLogo logo;

    @OneToOne(mappedBy = "vendor")
    private VendorAddress address;

//    @OneToMany(mappedBy = "vendor")
//    private Set<Inventory> inventories = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
//        return (long) inventoryHistories.size() > 0 ;
        return false;
    }
}

