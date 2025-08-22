package com.team10.smarthospital.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientProfileRequest {

    private String chiefComplaint;

    private String allergies;

    private String pastMedicalConditions;

    private String currentMedications;

    private String pastSurgicalHistory;

    private String pastMedications;

    private String familyHistory;

    private String socialHistory;
}
