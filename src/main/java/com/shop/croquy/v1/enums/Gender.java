package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.WRONG_ATTRIBUTE_TYPE;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE,
    FEMALE,
    UNKNOWN;

    public static Gender getEnumFromString(String str) {
        if(Objects.equals(str, MALE.toString())) return MALE;
        else if(Objects.equals(str, FEMALE.toString())) return FEMALE;
        else if(Objects.equals(str, UNKNOWN.toString())) return UNKNOWN;

        throw new IllegalArgumentException(WRONG_ATTRIBUTE_TYPE);
    }
}