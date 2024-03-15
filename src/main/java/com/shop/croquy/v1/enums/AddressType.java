package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    BILLING,
    SHIPPING,
    DEFAULT
}