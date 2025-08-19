package com.team10.smarthospital.model.entity;

import com.team10.smarthospital.model.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends BaseEntity {

    private String userId;

    private String firstName;

    private String lastName;

    private Integer gender;

    private String email;

    private String password;

    private Integer role;

    private String mobileNumber;

    private String avatarUrl;

    public String getFullName() {
        if (Role.DOCTOR.getRoleCode().equals(this.role)) {
            return "Dr." + this.firstName + " " + this.getLastName();
        }
        return this.firstName + " " + this.getLastName();
    }
}
