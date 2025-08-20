package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum DoctorDepartment {
    GENERAL_MEDICINE(1, "General Medicine"),
    GENERAL_SURGERY(2, "General Surgery"),
    CARDIOLOGY(3, "Cardiology"),
    NEPHROLOGY(4, "Nephrology"),
    PEDIATRICS(5, "Pediatrics");

    private final Integer departmentCode;
    private final String departmentName;

    DoctorDepartment(Integer departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public static DoctorDepartment getDoctorDepartment(Integer departmentCode) {
        for (DoctorDepartment department : DoctorDepartment.values()) {
            if (department.getDepartmentCode().equals(departmentCode)) {
                return department;
            }
        }
        return null;
    }
}
