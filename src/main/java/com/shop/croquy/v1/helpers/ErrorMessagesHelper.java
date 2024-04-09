package com.shop.croquy.v1.helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessagesHelper {
    public static final String CUSTOMER_NOT_AUTHORIZED = "You are not authorize to login has a backoffice user";
    public static final String USER_USERNAME_NOT_FOUND = "User not found with username";
    public static final String INCORRECT_LOGIN_PASSWORD = "Incorrect login or password";
    public static final String SHOP_NAME_ALREADY_EXIST = "Shop already exist with name: ";
    public static final String SHOP_SLUG_ALREADY_EXIST = "Shop already exist with slug: ";
    public static final String SHOP_ID_NOT_FOUND = "Shop not found with id: ";
    public static final String COUNTRY_NAME_ALREADY_EXIST = "Country already exist with name: ";
    public static final String COUNTRY_ID_NOT_FOUND = "Country not found with id: ";
}
