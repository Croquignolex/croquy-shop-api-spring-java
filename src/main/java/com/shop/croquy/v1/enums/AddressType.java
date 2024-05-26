package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum AddressType {
    BILLING,
    SHIPPING,
    DEFAULT;

    public boolean equals(String val) {
        return Objects.equals(val, this.toString());
    }

    public boolean exist(String val) {
        return Objects.equals(val, this.toString());
    }

    //public String getEnumFromString(String str) {
    // return Objects.equals(val, this.toString());
    //}
}