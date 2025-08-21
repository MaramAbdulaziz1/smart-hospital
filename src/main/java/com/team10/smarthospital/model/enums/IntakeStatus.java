package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum IntakeStatus {
    DRAFT(0, "DRAFT"),
    SUBMITTED(1, "SUBMITTED"),
    COMPLETED(3, "COMPLETED");

    private final Integer statusCode;
    private final String statusName;

    IntakeStatus(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }
}
