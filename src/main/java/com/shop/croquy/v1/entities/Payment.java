package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}





