package com.swd.bike.enums;

public enum ResponseCode {
    // Common
    SUCCESS(0, "Success"),
    FAILED(1, "Failed"),
    COMMON_ERROR(2, "Common Error"),
    INVALID_PARAM(3, "Invalid param"),
    INVALID_SESSION(4, "Invalid session"),
    UNHANDLED_REQUEST(5, "Unhandled request"),
    THIRD_PARTY_ERROR(6, "Error when calling third party"),
    JSON_PROCESSING_ERROR(7, "Error when processing JSON"),

    // Auth
    INVALID_USERNAME_OR_PASSWORD(10, "Invalid username or password"),
    GOOGLE_AUTH_ERROR(11, "Error occurs when login with Google"),
    REFRESH_TOKEN_INVALID(12, "Invalid refresh token"),
    AUTHENTICATION_FAILED(13, "Authentication failed"),
    AUTHENTICATION_FAILED_OUTSIDE_EMAIL(14, "Authentication failed: Outside email"),
    CODE_INVALID(15, "Invalid code"),

    // User
    USER_NOT_FOUND(20, "User not found"),
    USER_MISSING_FIELD(21, "User missing required field"),

    ;


    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
