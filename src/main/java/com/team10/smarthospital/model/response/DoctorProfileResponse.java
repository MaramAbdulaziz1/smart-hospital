package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorProfileResponse extends ProfileResponse {

    private String departmentName;

    private String employeeId;

    private String location;

    private String workNumber;
}
