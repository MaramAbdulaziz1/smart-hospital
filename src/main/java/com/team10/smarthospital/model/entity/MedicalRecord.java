package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecord extends BaseEntity {
    private Integer recordId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private String prescription;
}
