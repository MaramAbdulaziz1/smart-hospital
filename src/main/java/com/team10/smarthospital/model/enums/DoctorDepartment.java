package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum DoctorDepartment {
    INTERNAL_MEDICINE(0, "INTERNAL_MEDICINE"),
    SURGERY(0, "SURGERY"),
    OBSTETRICS_AND_GYNECOLOGY(0, "OBSTETRICS_AND_GYNECOLOGY"),
    PEDIATRICS(0, "PEDIATRICS"),
    ENT(0, "ENT");

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
