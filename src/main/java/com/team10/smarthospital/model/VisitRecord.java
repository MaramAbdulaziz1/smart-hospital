package com.team10.smarthospital.model;

public class VisitRecord {
    public String date;
    public String department;
    public String diagnosis;
    public String doctor;
    //Satvika
    String doctorsNotes;
    String treatmentPlan;
    String patientId; // Mock patient ID
    // Mock vitals data
    String heartRate;
    String bloodPressure;
    String height;
    String weight;
    String temperature;
    // endsatvika
    public VisitRecord() {
        // Default constructor
    }
    public VisitRecord(String date, String department, String diagnosis, String doctor) {
        this.date = date;
        this.department = department;
        this.diagnosis = diagnosis;
        this.doctor = doctor;
    }
    public String getDate() {
      return date;
    }
    public void setDate(String date) {
      this.date = date;
    }
    public String getDepartment() {
      return department;
    }
    public void setDepartment(String department) {
      this.department = department;
    }
    public String getDiagnosis() {
      return diagnosis;
    }
    public void setDiagnosis(String diagnosis) {
      this.diagnosis = diagnosis;
    }
    public String getDoctor() {
      return doctor;
    }
    public void setDoctor(String doctor) {
      this.doctor = doctor;
    }
    public String getDoctorsNotes() {
      return doctorsNotes;
    }
    public void setDoctorsNotes(String doctorsNotes) {
      this.doctorsNotes = doctorsNotes;
    }
    public String getTreatmentPlan() {
      return treatmentPlan;
    }
    public void setTreatmentPlan(String treatmentPlan) {
      this.treatmentPlan = treatmentPlan;
    }
    public String getPatientId() {
      return patientId;
    }
    public void setPatientId(String patientId) {
      this.patientId = patientId;
    }
    public String getHeartRate() {
      return heartRate;
    }
    public void setHeartRate(String heartRate) {
      this.heartRate = heartRate;
    }
    public String getBloodPressure() {
      return bloodPressure;
    }
    public void setBloodPressure(String bloodPressure) {
      this.bloodPressure = bloodPressure;
    }
    public String getHeight() {
      return height;
    }
    public void setHeight(String height) {
      this.height = height;
    }
    public String getWeight() {
      return weight;
    }
    public void setWeight(String weight) {
      this.weight = weight;
    }
    public String getTemperature() {
      return temperature;
    }
    public void setTemperature(String temperature) {
      this.temperature = temperature;
    }

}
