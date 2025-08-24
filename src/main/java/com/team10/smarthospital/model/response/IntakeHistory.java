package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IntakeHistory {

    private String intakeId;
    private String appointmentId;
    private String complaint;
    private String notes;
    private String diagnosis;
    private String followUpPlan;
    private String doctorName;
    private String departmentName;
    private LocalDate date;
}
