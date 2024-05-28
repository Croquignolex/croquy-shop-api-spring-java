package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.WRONG_ATTRIBUTE_TYPE;

@Getter
@RequiredArgsConstructor
public enum Role {
    SUPER_ADMIN,
    ADMIN,
    MANAGER,
    SELLER,
    CUSTOMER;

    public static Role getEnumFromString(String str) {
        if(Objects.equals(str, SUPER_ADMIN.toString())) return SUPER_ADMIN;
        else if(Objects.equals(str, ADMIN.toString())) return ADMIN;
        else if(Objects.equals(str, MANAGER.toString())) return MANAGER;
        else if(Objects.equals(str, SELLER.toString())) return SELLER;
        else if(Objects.equals(str, CUSTOMER.toString())) return CUSTOMER;

        throw new IllegalArgumentException(WRONG_ATTRIBUTE_TYPE);
    }
}