package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Prescription extends BaseEntity {

    private String prescriptionId;

    private String appointmentId;

    private String diagnosis;

    private String drugAllergy;

    private List<String> medicationId = new ArrayList<>();
}
