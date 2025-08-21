package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    PATIENT(0, "PATIENT"),
    DOCTOR(1, "DOCTOR"),
    NURSE(2, "NURSE");

    private final Integer roleCode;
    private final String roleName;

    Role(Integer roleCode, String roleName) {
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public static Role getRoleByRoleCode(Integer roleCode) {
        for (Role role : Role.values()) {
            if (role.getRoleCode().equals(roleCode)) {
                return role;
            }
        }
        return null;
    }

    public String getAuthority() {
        return "ROLE_" + this.roleName;
    }
}
