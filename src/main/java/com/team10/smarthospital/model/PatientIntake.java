package com.team10.smarthospital.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "patient_intakes")
public class PatientIntake extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientProfile patient;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", nullable = false)
    private VisitType visitType;

    @NotBlank(message = "Complaints are required")
    @Column(name = "complaints", columnDefinition = "TEXT")
    private String complaints;

    @Column(name = "diagnosis", columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "medical_history", columnDefinition = "TEXT")
    private String medicalHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IntakeStatus status = IntakeStatus.DRAFT;

    @Column(name = "phone_call")
    private Boolean phoneCall = false;

    @Column(name = "video_call")
    private Boolean videoCall = false;

    // Default constructor
    public PatientIntake() {
    }

    // Constructor with required fields
    public PatientIntake(PatientProfile patient, VisitType visitType, String complaints) {
        this.patient = patient;
        this.visitType = visitType;
        this.complaints = complaints;
        this.status = IntakeStatus.DRAFT;
    }

    // Getters and Setters
    public PatientProfile getPatient() {
        return patient;
    }

    public void setPatient(PatientProfile patient) {
        this.patient = patient;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public String getComplaints() {
        return complaints;
    }

    public void setComplaints(String complaints) {
        this.complaints = complaints;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public IntakeStatus getStatus() {
        return status;
    }

    public void setStatus(IntakeStatus status) {
        this.status = status;
    }

    public Boolean getPhoneCall() {
        return phoneCall;
    }

    public void setPhoneCall(Boolean phoneCall) {
        this.phoneCall = phoneCall;
    }

    public Boolean getVideoCall() {
        return videoCall;
    }

    public void setVideoCall(Boolean videoCall) {
        this.videoCall = videoCall;
    }

    @Override
    public String toString() {
        return "PatientIntake{" +
                "id=" + getId() +
                ", patient=" + (patient != null ? patient.getId() : null) +
                ", visitType=" + visitType +
                ", status=" + status +
                ", complaints='" + complaints + '\'' +
                '}';
    }
}
