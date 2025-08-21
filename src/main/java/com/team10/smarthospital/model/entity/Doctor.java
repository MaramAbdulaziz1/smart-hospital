package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor extends User {

    private Integer department;

    private String employeeId;

    private String location;

    private String workNumber;
}
