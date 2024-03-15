package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_SUPER_ADMIN,
    ROLE_ADMIN,
    ROLE_CUSTOMER
}
