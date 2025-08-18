package com.team10.smarthospital.model;

import java.time.LocalDateTime;

public class Vitals {
    private String patientId;
    private String patientName;
    private LocalDateTime recordedAt;
    private String recordedBy;
    
    // Vitals measurements
    private Double heartRate; // bpm
    private String bloodPressure; // mmHg (e.g., "120/80")
    private Double temperature; // °C
    private Double weight; // kg
    private Double height; // cm
    private Double bmi; // kg/m²
    private Double bloodGlucose; // mg/dl
    private Double oxygenSaturation; // %
    private Double respiratoryRate; // breaths/min

    // Default constructor
    public Vitals() {
        this.recordedAt = LocalDateTime.now();
    }

    // Constructor with patient info
    public Vitals(String patientId, String patientName, String recordedBy) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.recordedBy = recordedBy;
        this.recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

    public String getRecordedBy() {
        return recordedBy;
    }

    public void setRecordedBy(String recordedBy) {
        this.recordedBy = recordedBy;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(Double bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public Double getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Double oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Double getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Double respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    @Override
    public String toString() {
        return "Vitals{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", recordedAt=" + recordedAt +
                ", recordedBy='" + recordedBy + '\'' +
                ", heartRate=" + heartRate +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", temperature=" + temperature +
                ", weight=" + weight +
                ", height=" + height +
                ", bmi=" + bmi +
                ", bloodGlucose=" + bloodGlucose +
                ", oxygenSaturation=" + oxygenSaturation +
                ", respiratoryRate=" + respiratoryRate +
                '}';
    }
}
