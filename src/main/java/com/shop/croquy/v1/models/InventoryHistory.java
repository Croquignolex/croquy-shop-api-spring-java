package com.shop.croquy.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inventory_histories")
public class InventoryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "old_quantity", nullable = false)
    private Integer oldQuantity = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "deleted_at")
    private Date deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id", nullable = false)
    private User creator;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="shop_id", nullable = false)
    private Shop shop;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}