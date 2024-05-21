package com.shop.croquy.v1.dto.backoffice.coupon;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CouponUpdateRequest {
    @NotNull(message = "Discount field is required")
    protected Integer discount;

    @NotNull(message = "Total Use field is required")
    protected Integer totalUse;

    @NotEmpty(message = "Promotion Started Date field is required")
    protected String promotionStartedAt;

    @NotEmpty(message = "Promotion Ended Date field is required")
    protected String promotionEndedAt;

    protected String description;
}