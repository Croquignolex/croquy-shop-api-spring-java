package com.shop.croquy.v1.helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessagesHelper {
    public static final String CUSTOMER_NOT_AUTHORIZED = "You are not authorize to login has a backoffice user";
    public static final String USER_USERNAME_NOT_FOUND = "User not found with username";
    public static final String INCORRECT_LOGIN_PASSWORD = "Incorrect login or password";
    public static final String SHOP_NAME_ALREADY_EXIST = "Shop already exist with name: ";
    public static final String SHOP_SLUG_ALREADY_EXIST = "Shop already exist with slug: ";
    public static final String SHOP_NOT_FOUND = "Shop not found ";
    public static final String SHOP_CAN_NOT_BE_DELETED = "Shop can not be deleted";
    public static final String COUNTRY_NAME_ALREADY_EXIST = "Country already exist with name: ";
    public static final String COUNTRY_NOT_FOUND = "Country not found";
    public static final String COUNTRY_CAN_NOT_BE_DELETED = "Country can not be deleted";
    public static final String FLAG_NOT_FOUND = "Flag not found";
    public static final String LOGO_NOT_FOUND = "Logo not found";
    public static final String ADDRESS_NOT_FOUND = "Address not found";
    public static final String STATE_NAME_ALREADY_EXIST = "State already exist in this country with name: ";
    public static final String STATE_NOT_FOUND = "State not found";
    public static final String VENDOR_NAME_ALREADY_EXIST = "Vendor already exist with name: ";
    public static final String VENDOR_CAN_NOT_BE_DELETED = "Vendor can not be deleted";
    public static final String VENDOR_NOT_FOUND = "Vendor not found";
    public static final String BRAND_NAME_ALREADY_EXIST = "Brand already exist with name: ";
    public static final String BRAND_CAN_NOT_BE_DELETED = "Brand can not be deleted";
    public static final String BRAND_NOT_FOUND = "Brand not found";
    public static final String BRAND_SLUG_ALREADY_EXIST = "Brand already exist with slug: ";
    public static final String COUPON_CODE_ALREADY_EXIST = "Coupon already exist in this country with code: ";
    public static final String COUPON_CAN_NOT_BE_DELETED = "Coupon can not be deleted";
    public static final String COUPON_NOT_FOUND = "Coupon not found";
    public static final String FILE_SYSTEM_ERROR = "File system error";
    public static final String FILE_SIZE_EXCEEDS = "File size exceeds maximum limit";
    public static final String WRONG_ORIGINAL_FILE_NAME = "Wrong original file name";
    public static final String WRONG_ORIGINAL_FILE_TYPE = "Wrong original file type";
    public static final String WRONG_COUPON_UPDATE_VALUE = "Wrong coupon discount/total usage update value";
}
