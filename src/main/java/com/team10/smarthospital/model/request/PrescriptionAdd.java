package com.team10.smarthospital.model.request;

import com.team10.smarthospital.model.entity.Medication;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrescriptionAdd {

    private String appointmentId;

    private String diagnosis;

    private String drugAllergy;

    private List<Medication> medications;
}
