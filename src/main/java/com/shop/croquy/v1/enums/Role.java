package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_SUPER_ADMIN("super_admin"),
    ROLE_ADMIN("admin"),
    ROLE_CUSTOMER("customer");

    private final String displayValue;
}
