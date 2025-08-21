package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    PENDING(0, "PENDING"),
    COMPLETED(0, "COMPLETED"),
    CANCELLED(0, "CANCELLED");

    private final Integer statusCode;
    private final String statusName;

    AppointmentStatus(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    public static AppointmentStatus getStatusByCode(Integer code) {
        for (AppointmentStatus appointmentStatus : AppointmentStatus.values()) {
            if (appointmentStatus.getStatusCode().equals(code)) {
                return appointmentStatus;
            }
        }
        return null;
    }
}
