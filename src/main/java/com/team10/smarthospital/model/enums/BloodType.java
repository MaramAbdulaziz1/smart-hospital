package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum BloodType {
    A_POSITIVE(0, "A+"),
    A_NEGATIVE(1, "A-"),
    B_POSITIVE(2, "B+"),
    B_NEGATIVE(3, "B-"),
    AB_POSITIVE(4, "AB+"),
    AB_NEGATIVE(5, "AB-"),
    O_POSITIVE(6, "O+"),
    O_NEGATIVE(7, "O-"),
    OTHER(8, "Other");

    private final Integer bloodCode;
    private final String bloodType;

    BloodType(Integer bloodCode, String bloodType) {
        this.bloodCode = bloodCode;
        this.bloodType = bloodType;
    }

    public static String getBloodTypeName(Integer bloodTypeCode) {
        for (BloodType bloodType : BloodType.values()) {
            if (bloodType.getBloodCode().equals(bloodTypeCode)) {
                return bloodType.getBloodType();
            }
        }
        return null;
    }
}
