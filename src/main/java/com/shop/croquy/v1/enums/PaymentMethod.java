package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CASH_ON_DELIVERY,
    ORANGE_MONEY,
    MTN_MOBILE_MONEY,
    STRIPE,
    PAYPAL,
}