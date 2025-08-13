package com.team10.smarthospital.model;

public enum AppointmentStatus {
  UPCOMING("Upcoming"),
  COMPLETED("Completed"),
  CANCELLED("Cancelled"),
  NO_SHOW("No Show"),
  RESCHEDULED("Rescheduled");

  private final String displayName;

  AppointmentStatus(String displayName) {
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
