package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VisitRecord {
    private String patientCode;
    private String patientName;
    private LocalDate dateOfVisit;
    private String doctorName;
    private String department;
    private String diagnosis;
    private String doctorsNotes;
    private String treatmentPlan;
    private List<String> medicationsPrescribed;
    private String pastMedicalConditions;
    private String pastMedications;
    private List<String> currentMedications;
    private List<String> surgicalHistory;
    private String allergies;
    private String familyHistory;
    private String socialHistory;
    private String pastSurgicalHistory;
}
