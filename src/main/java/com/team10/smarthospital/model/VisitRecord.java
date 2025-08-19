//This is the package name that all classes are in
package com.team10.smarthospital.model;

import java.util.List;

public class VisitRecord {
  Long patientId;
  String patientName;


  String dateOfVisit;
  String doctorName;
  String department;

  String diagnosis;

  String doctorsNotes;
  List<String> treatmentPlan;



  List<String> medicationsPrescribed;
  List<String> pastMedicalConditions;
  List<String> pastMedications;
  List<String> currentMedications;
  List<String> surgicalHistory;
  List<String> allergies;
  List<String> familyHistory;
  List<String> socialHistory;

  public VisitRecord() {
    // Default constructor
  }

  public VisitRecord(String dateOfVisit, String department, String diagnosis, String doctorName) {
    this.dateOfVisit = dateOfVisit;
    this.department = department;
    this.diagnosis = diagnosis;
    this.doctorName = doctorName;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getDateOfVisit() {
    return dateOfVisit;
  }

  public void setDateOfVisit(String dateOfVisit) {
    this.dateOfVisit = dateOfVisit;
  }

  public String getDoctorName() {
    return doctorName;
  }

  public void setDoctorName(String doctorName) {
    this.doctorName = doctorName;
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

  public String getDoctorsNotes() {
    return doctorsNotes;
  }

  public void setDoctorsNotes(String doctorsNotes) {
    this.doctorsNotes = doctorsNotes;
  }

  public List<String> getTreatmentPlan() {
    return treatmentPlan;
  }
  public void setTreatmentPlan(List<String> treatmentPlan) {
    this.treatmentPlan = treatmentPlan;
  }

  public List<String> getMedicationsPrescribed() {
    return medicationsPrescribed;
  }

  public void setMedicationsPrescribed(List<String> medicationsPrescribed) {
    this.medicationsPrescribed = medicationsPrescribed;
  }

  public List<String> getPastMedicalConditions() {
    return pastMedicalConditions;
  }

  public void setPastMedicalConditions(List<String> pastMedicalConditions) {
    this.pastMedicalConditions = pastMedicalConditions;
  }

  public List<String> getPastMedications() {
    return pastMedications;
  }

  public void setPastMedications(List<String> pastMedications) {
    this.pastMedications = pastMedications;
  }

  public List<String> getCurrentMedications() {
    return currentMedications;
  }

  public void setCurrentMedications(List<String> currentMedications) {
    this.currentMedications = currentMedications;
  }

  public List<String> getSurgicalHistory() {
    return surgicalHistory;
  }

  public void setPastSurgicalHistory(List<String> surgicalHistory) {
    this.surgicalHistory = surgicalHistory;
  }

  public List<String> getAllergies() {
    return allergies;
  }

  public void setAllergies(List<String> allergies) {
    this.allergies = allergies;
  }

  public List<String> getFamilyHistory() {
    return familyHistory;
  }

  public void setFamilyHistory(List<String> familyHistory) {
    this.familyHistory = familyHistory;
  }

  public List<String> getSocialHistory() {
    return socialHistory;
  }

  public void setSocialHistory(List<String> socialHistory) {
    this.socialHistory = socialHistory;
  }

  @Override
  public String toString() {
    return "VisitRecord [patientId=" + patientId + ", patientName=" + patientName + ", dateOfVisit=" + dateOfVisit
        + ", doctorName=" + doctorName + ", department=" + department + ", diagnosis=" + diagnosis + ", doctorsNotes="
        + doctorsNotes + ", treatmentPlan=" + treatmentPlan + ", medicationsPrescribed=" + medicationsPrescribed
        + ", pastMedicalConditions=" + pastMedicalConditions + ", pastMedications=" + pastMedications
        + ", currentMedications=" + currentMedications + ", surgicalHistory=" + surgicalHistory + ", allergies="
        + allergies + ", familyHistory=" + familyHistory + ", socialHistory=" + socialHistory + "]";
  }
}
