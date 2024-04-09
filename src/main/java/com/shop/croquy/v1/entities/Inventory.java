package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.InventoryCondition;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "inventories")
public class Inventory extends BaseEntity {
    @Column(name = "condition", nullable = false)
    @Enumerated(EnumType.STRING)
    private InventoryCondition condition = InventoryCondition.NEW;

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

    @Column(name = "promotion_started_at")
    private Date promotionStartedAt;

    @Column(name = "promotion_ended_at")
    private Date promotionEndedAt;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

//    @ManyToOne
//    @JoinColumn(name = "vendor_id")
//    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}