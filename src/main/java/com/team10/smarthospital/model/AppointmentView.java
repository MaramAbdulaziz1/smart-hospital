package com.team10.smarthospital.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * AppointmentView entity representing a medical appointment
 * Core data structure for appointment management
 */
public class AppointmentView {

  private Long id;
  private Long patientId;
  private String doctorName;
  private String department;
  private LocalDate date;
  private LocalTime time;
  private AppointmentStatusView status;
  private AppointmentType type;
  private String notes;
  private LocalDate createdAt;
  private LocalDate updatedAt;
  private String nurseName;

  // Default constructor
  public AppointmentView() {
    this.createdAt = LocalDate.now();
    this.updatedAt = LocalDate.now();
    this.status = AppointmentStatusView.UPCOMING;
  }

  // Constructor with essential fields
  public AppointmentView(Long patientId, String doctorName, LocalDate date, LocalTime time) {
    this();
    this.patientId = patientId;
    this.doctorName = doctorName;
    this.date = date;
    this.time = time;
  }

  // Getters and Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getPatientId() { return patientId; }
  public void setPatientId(Long patientId) { this.patientId = patientId; }

  public String getDoctorName() { return doctorName; }
  public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

  public String getDepartment() { return department; }
  public void setDepartment(String department) { this.department = department; }

  public LocalDate getDate() { return date; }
  public void setDate(LocalDate date) { this.date = date; }

  public LocalTime getTime() { return time; }
  public void setTime(LocalTime time) { this.time = time; }

  public AppointmentStatusView getStatus() { return status; }
  public void setStatus(AppointmentStatusView status) {
    this.status = status;
    this.updatedAt = LocalDate.now();
  }

  public AppointmentType getType() { return type; }
  public void setType(AppointmentType type) { this.type = type; }

  public String getNotes() { return notes; }
  public void setNotes(String notes) { this.notes = notes; }

  public LocalDate getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

  public LocalDate getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }

  public String getNurseName() { return nurseName; }
  public void setNurseName(String nurseName) { this.nurseName = nurseName; }

  // Business logic methods

  /**
   * Check if appointment is upcoming (future date/time)
   */
  public boolean isUpcoming() {
    return status == AppointmentStatusView.UPCOMING &&
      (date.isAfter(LocalDate.now()) ||
        (date.isEqual(LocalDate.now()) && time.isAfter(LocalTime.now())));
  }

  /**
   * Check if appointment can be cancelled
   */
  public boolean canBeCancelled() {
    return status == AppointmentStatusView.UPCOMING && isUpcoming();
  }

  // Formatting methods for Thymeleaf templates

  /**
   * Get formatted date string for display (dd MM yyyy)
   */
  public String getFormattedDate() {
    return date != null ? date.format(java.time.format.DateTimeFormatter.ofPattern("dd MM yyyy")) : "";
  }

  /**
   * Get formatted time string for display (HH:mm)
   */
  public String getFormattedTime() {
    return time != null ? time.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")) : "";
  }

  /**
   * Get status display name for UI
   */
  public String getStatusDisplayName() {
    return status != null ? status.getDisplayName() : "";
  }

  /**
   * Get lowercase status for CSS class binding
   */
  public String getStatusLowerCase() {
    return status != null ? status.name().toLowerCase() : "";
  }

  @Override
  public String toString() {
    return "AppointmentView{" +
      "id=" + id +
      ", patientId=" + patientId +
      ", doctorName='" + doctorName + '\'' +
      ", nurseName='" + nurseName + '\'' +
      ", date=" + date +
      ", time=" + time +
      ", status=" + status +
      '}';
  }
}
