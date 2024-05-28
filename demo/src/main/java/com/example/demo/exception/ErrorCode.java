package com.example.demo.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_EXIST(1001, "user exist"),
    INVALID_ENUMKEY(1002, "Invalid message key"),
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception error"),
    USERNAME_INVALID(1003, "Username must be at least five character"),
    PASSWORD_INVALID(1004, "Password must be at least eight character"),
    USER_NOT_FOUND(1005, "user not found"),
    JSON_EXCEPTION(1006, "JSONException")
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
