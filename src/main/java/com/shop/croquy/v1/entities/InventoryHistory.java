package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "inventory_histories")
public class InventoryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "old_quantity", nullable = false)
    private Integer oldQuantity = 0;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}