package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "shops")
public class Shop extends BaseEntity {
    @NotNull(message = "Shop name is required")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

//    @OneToMany(mappedBy = "shop")
//    private Set<InventoryHistory> inventoryHistories = new HashSet<>();
}



