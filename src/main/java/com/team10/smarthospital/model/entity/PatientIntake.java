package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientIntake extends BaseEntity {

    private String intakeId;

    private String appointmentId;

    private String complaint;

    private String notes;

    private String diagnosis;

    private String followUpPlan;
}
