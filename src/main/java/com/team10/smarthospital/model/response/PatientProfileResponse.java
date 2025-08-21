package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PatientProfileResponse extends ProfileResponse {

    private String patientCode;

    private BigDecimal height;

    private BigDecimal weight;

    private Integer bloodType;

    private String address;

    private String nationalId;

    private String nearestClinic;

    private String ecFirstName;

    private String ecLastName;

    private Integer ecRelationship;

    private String ecMobileNumber;

    private String chiefComplaint;

    private String allergies;

    private String pastMedicalConditions;

    private String currentMedications;

    private String pastSurgicalHistory;

    private String pastMedications;

    private String familyHistory;

    private String socialHistory;
}
