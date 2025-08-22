package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileResponse {

    private String firstName;

    private String lastName;

    private LocalDate birth;

    private String genderName;

    private String email;

    private Integer role;

    private String roleName;

    private String mobileNumber;

    private String avatarBase64;
}
