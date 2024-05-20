package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "coupons")
public class Coupon extends BaseEntity {
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "discount", nullable = false)
    private Integer discount = 0;

    @Column(name = "total_use", nullable = false)
    private Integer totalUse = 0;

    @Column(name = "total_usage", nullable = false)
    private Integer totalUsage = 0;

    @Column(name = "promotion_started_at")
    private Date promotionStartedAt;

    @Column(name = "promotion_ended_at")
    private Date promotionEndedAt;

    @JsonIgnore
    public boolean isNonDeletable() {
        return totalUsage == 0 ;
    }
}



