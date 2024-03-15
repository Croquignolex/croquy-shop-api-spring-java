package com.shop.croquy.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "discount", nullable = false)
    private Integer discount = 0;

    @Column(name = "total_use", nullable = false)
    private Integer totalUse = 0;

    @Column(name = "promotion_started_at")
    private Date promotionStartedAt;

    @Column(name = "promotion_ended_at")
    private Date promotionEndedAt;

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

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}



