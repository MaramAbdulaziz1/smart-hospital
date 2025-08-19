package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0, "MALE"),
    FEMALE(1, "FEMALE"),
    OTHER(2, "OTHER");

    private final Integer genderCode;
    private final String genderName;

    Gender(Integer genderCode, String genderName) {
        this.genderCode = genderCode;
        this.genderName = genderName;
    }
}
