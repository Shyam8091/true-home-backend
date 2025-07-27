package com.example.true_home.util;

public final class TrueHomeConstants {
    public static final String ROOT = "/truehome/api";
    public static final String GENERATE_OTP = "/generateOtp";
    public static final String WISHLIST = "/wishlist";

    public static final String VERIFY_OTP = "/verifyOtp";
    public static final String USER_SIGNUP = "/signup";
    public static final String LISTING = "/listing";
    public static final String LISTING_BY_ID = "/listing/{id}";
    public static final String ACCOUNT_LISTING = "/account/listing";
    public static final String ORDERS = "/order";
    public static final String USER = "/user";
    public static final String DASHBOARD = "/dashboard";

    public static final String VALIDATION_ERROR_CODE = "MBD-001";
    public static final String USER_NOT_FOUND = "User Not Found";
    public static final String GENERAL_EXCEPTION_ERROR_MESSAGE = "An error occured while processing the request. Please retry after sometime";

    public static final String GENERAL_EXCEPTION_ERROR_CODE = "E500";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation Failure.";
    public static final long SESSION_EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 60 minutes
    private static final long MAX_SESSION_LIFETIME = 24 * 60 * 60 * 1000; // 24 hours
}
