package com.shop.croquy.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.shop.croquy.v1.helpers.ErrorMessagesHelper.WRONG_ATTRIBUTE_TYPE;

@Getter
@RequiredArgsConstructor
public enum AttributeType {
    TEXT,
    SELECT,
    COLOR;

    public static AttributeType getEnumFromString(String str) {
        if(Objects.equals(str, SELECT.toString())) return SELECT;
        else if(Objects.equals(str, COLOR.toString())) return COLOR;
        else if(Objects.equals(str, TEXT.toString())) return TEXT;

        throw new IllegalArgumentException(WRONG_ATTRIBUTE_TYPE);
    }
}