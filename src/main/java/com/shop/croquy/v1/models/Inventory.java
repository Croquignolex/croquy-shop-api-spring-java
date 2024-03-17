package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.InventoryCondition;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "condition", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryCondition condition = InventoryCondition.NEW;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "alert_quantity", nullable = false)
    private Integer alertQuantity = 0;

    @Column(name = "delivery_price", nullable = false)
    private Integer deliveryPrice = 0;

    @Column(name = "purchase_price", nullable = false)
    private Integer purchasePrice = 0;

    @Column(name = "sale_price", nullable = false)
    private Integer salePrice = 0;

    @Column(name = "promotion_price", nullable = false)
    private Integer promotionPrice = 0;

    @Column(name = "description")
    private String description;

    @Column(name = "promotion_started_at")
    private Date promotionStartedAt;

    @Column(name = "promotion_ended_at")
    private Date promotionEndedAt;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}