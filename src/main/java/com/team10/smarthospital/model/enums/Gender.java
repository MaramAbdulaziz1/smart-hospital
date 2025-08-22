package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE(0, "Male"),
    FEMALE(1, "FeMale"),
    OTHER(2, "Other");

    private final Integer genderCode;
    private final String genderName;

    Gender(Integer genderCode, String genderName) {
        this.genderCode = genderCode;
        this.genderName = genderName;
    }

    public static String getGenderName(Integer genderCode) {
        for (Gender gender : Gender.values()) {
            if (gender.getGenderCode().equals(genderCode)) {
                return gender.getGenderName();
            }
        }
        return null;
    }
}
