package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0", "SUCCESS"),

    USER_NOT_FOUND("40401", "User not found"),

    PARAMETER_EMPTY("40001", "Request parameter is empty"),
    PARAMETER_INVALID("40002", "Invalid request parameter"),

    UNAUTHORIZED("40100", "Unauthorized"),
    ACCESS_DENIED("40300", "Access denied"),

    RESOURCE_NOT_FOUND("40400", "Resource not found"),

    SERVER_ERROR("50000", "Internal server error"),
    SERVICE_UNAVAILABLE("50300", "Service unavailable");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
