package com.shop.croquy.v1.dto.backoffice.coupon;

import com.shop.croquy.v1.entities.Coupon;
import com.shop.croquy.v1.entities.User;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class CouponStoreRequest {
    @NotEmpty(message = "Code field is required")
    protected String code;

    @NotEmpty(message = "Discount field is required")
    protected Integer discount;

    @NotEmpty(message = "Total Use field is required")
    protected Integer totalUse;

    @NotEmpty(message = "Promotion Started Date field is required")
    protected Date promotionStartedAt;

    @NotEmpty(message = "Promotion Ended Date field is required")
    protected Date promotionEndedAt;

    protected String description;

    public Coupon toCoupon(User creator) {
        Coupon coupon = new Coupon();

        coupon.setCode(code);
        coupon.setDiscount(discount);
        coupon.setTotalUse(totalUse);
        coupon.setPromotionStartedAt(promotionStartedAt);
        coupon.setPromotionEndedAt(promotionEndedAt);
        coupon.setDescription(description);
//        coupon.setCreator(creator);

        return coupon;
    }
}