package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0", "SUCCESS");

    private final String code;
    private final String codeName;

    ResponseCode(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
}
