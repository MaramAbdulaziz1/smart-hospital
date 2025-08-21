package com.team10.smarthospital.model;

public enum AppointmentStatusView {
  UPCOMING("Upcoming"),
  COMPLETED("Completed"),
  CANCELLED("Cancelled");

  private final String displayName;

  AppointmentStatusView(String displayName) {
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
