package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum VisitType {
    FIRST_VISIT(0, "FIRST_VISIT"),
    FOLLOW_UP(1, "FOLLOW_UP"),
    EMERGENCY(2, "EMERGENCY"),
    ROUTINE_CHECKUP(3, "ROUTINE_CHECKUP");

    private final Integer visitCode;
    private final String visitType;

    VisitType(Integer visitCode, String visitType) {
        this.visitCode = visitCode;
        this.visitType = visitType;
    }
}
