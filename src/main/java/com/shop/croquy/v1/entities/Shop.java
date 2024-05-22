package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shop.croquy.v1.entities.address.ShopAddress;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_shops")
public class Shop extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @OneToOne(mappedBy = "shop")
    private ShopAddress address;

//    @OneToMany(mappedBy = "shop")
//    private Set<InventoryHistory> inventoryHistories = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
//        return (long) inventoryHistories.size() > 0 ;
        return false ;
    }
}



