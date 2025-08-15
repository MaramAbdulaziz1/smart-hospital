package com.team10.smarthospital.model;

public enum AppointmentType {
  CONSULTATION("Consultation"),
  VITAL_CHECK("Vital Check"),
  FOLLOW_UP("Follow Up"),
  SURGERY("Surgery"),
  THERAPY("Therapy"),
  VACCINATION("Vaccination"),
  DIAGNOSTIC("Diagnostic"),
  EMERGENCY("Emergency");

  private final String displayName;

  AppointmentType(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
