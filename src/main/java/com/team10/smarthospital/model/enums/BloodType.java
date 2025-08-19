package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum BloodType {
    A(0, "A"),
    B(1, "B"),
    AB(2, "AB"),
    O(3, "O");

    private final Integer bloodCode;
    private final String bloodType;

    BloodType(Integer bloodCode, String bloodType) {
        this.bloodCode = bloodCode;
        this.bloodType = bloodType;
    }
}
