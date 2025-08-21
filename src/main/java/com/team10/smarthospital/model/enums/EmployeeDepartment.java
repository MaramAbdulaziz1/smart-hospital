package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum EmployeeDepartment {

    GENERAL_MEDICINE(1, "General Medicine"),
    GENERAL_SURGERY(2, "General Surgery"),
    CARDIOLOGY(3, "Cardiology"),
    NEPHROLOGY(4, "Nephrology"),
    PEDIATRICS(5, "Pediatrics");

    private final Integer departmentCode;
    private final String departmentName;

    EmployeeDepartment(Integer departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public static EmployeeDepartment getDoctorDepartment(Integer departmentCode) {
        for (EmployeeDepartment department : EmployeeDepartment.values()) {
            if (department.getDepartmentCode().equals(departmentCode)) {
                return department;
            }
        }
        return null;
    }
}
