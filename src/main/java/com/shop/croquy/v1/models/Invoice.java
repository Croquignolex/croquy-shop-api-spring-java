package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.InvoiceStatus;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "reference", nullable = false, unique = true)
    private String reference;

    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "invoice")
    private Set<Payment> payments = new HashSet<>();

    public Boolean isPaid() {
        return status == InvoiceStatus.PAID;
    }

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}




