package com.shop.croquy.v1.enums;

public enum Role {
    ROLE_SUPER_ADMIN("super_admin"),
    ROLE_ADMIN("admin"),
    ROLE_CUSTOMER("customer");

    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
