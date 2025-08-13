package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.Appointment;
import com.team10.smarthospital.model.AppointmentStatus;
import com.team10.smarthospital.model.AppointmentType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing patient appointments
 * Handles appointment viewing, cancellation, and management operations
 */
@Controller
public class ManageAppointmentsController {

  /**
   * Display the manage appointments page
   * @param model Spring model for passing data to the view
   * @return template name for the appointments management page
   */
  @GetMapping("/patient/manageAppointments")
  public String manageAppointmentsPage(Model model) {
    // Create mock upcoming appointments data
    List<Appointment> upcomingAppointments = createMockUpcomingAppointments();

    // Create mock appointment history data
    List<Appointment> appointmentHistory = createMockAppointmentHistory();

    // Add data to model for template rendering
    model.addAttribute("upcomingAppointments", upcomingAppointments);
    model.addAttribute("appointmentHistory", appointmentHistory);
    model.addAttribute("pageTitle", "Manage Appointments");

    return "manageAppointments";
  }

  /**
   * Cancel an appointment via AJAX request
   * @param appointmentId ID of the appointment to cancel
   * @return JSON response indicating success or failure
   */
  @PostMapping("/patient/appointments/cancel/{appointmentId}")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> cancelAppointment(@PathVariable Long appointmentId) {
    Map<String, Object> response = new HashMap<>();

    try {
      // TODO: Replace with database logic when connecting to DB
      // Currently simulating cancellation success
      boolean success = simulateCancelAppointment(appointmentId);

      if (success) {
        response.put("success", true);
        response.put("message", "Appointment cancelled successfully");
      } else {
        response.put("success", false);
        response.put("message", "Unable to cancel this appointment, it may be expired or not exist");
      }

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      e.printStackTrace();
      response.put("success", false);
      response.put("message", "Error occurred while cancelling appointment: " + e.getMessage());
      return ResponseEntity.badRequest().body(response);
    }
  }

  /**
   * Refresh appointments list via AJAX request
   * @return JSON response with updated appointment lists
   */
  @GetMapping("/patient/appointments/refresh")
  @ResponseBody
  public ResponseEntity<Map<String, Object>> refreshAppointments() {
    Map<String, Object> response = new HashMap<>();

    try {
      List<Appointment> upcomingAppointments = createMockUpcomingAppointments();
      List<Appointment> appointmentHistory = createMockAppointmentHistory();

      response.put("success", true);
      response.put("upcomingAppointments", upcomingAppointments);
      response.put("appointmentHistory", appointmentHistory);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      e.printStackTrace();
      response.put("success", false);
      response.put("message", "Error occurred while refreshing appointments list");
      return ResponseEntity.badRequest().body(response);
    }
  }

  // =================== Private Methods (Mock Data) ===================

  /**
   * Create mock upcoming appointments for testing
   * TODO: Replace with repository calls when connecting to database
   */
  private List<Appointment> createMockUpcomingAppointments() {
    List<Appointment> appointments = new ArrayList<>();

    // Appointment 1
    Appointment appointment1 = new Appointment();
    appointment1.setId(1L);
    appointment1.setPatientId(1L);
    appointment1.setDoctorName("Oral Nurse");
    appointment1.setDepartment("Dentistry");
    appointment1.setDate(LocalDate.of(2025, 9, 5));
    appointment1.setTime(LocalTime.of(10, 0));
    appointment1.setStatus(AppointmentStatus.UPCOMING);
    appointment1.setType(AppointmentType.CONSULTATION);
    appointments.add(appointment1);

    // Appointment 2
    Appointment appointment2 = new Appointment();
    appointment2.setId(2L);
    appointment2.setPatientId(1L);
    appointment2.setDoctorName("Dr. Weasley");
    appointment2.setDepartment("General Medicine");
    appointment2.setDate(LocalDate.of(2025, 8, 27));
    appointment2.setTime(LocalTime.of(10, 0));
    appointment2.setStatus(AppointmentStatus.UPCOMING);
    appointment2.setType(AppointmentType.FOLLOW_UP);
    appointments.add(appointment2);

    return appointments;
  }

  /**
   * Create mock appointment history for testing
   * TODO: Replace with repository calls when connecting to database
   */
  private List<Appointment> createMockAppointmentHistory() {
    List<Appointment> history = new ArrayList<>();

    // Add upcoming appointments to history
    history.add(createAppointment(3L, "Oral Nurse", "Dentistry",
      LocalDate.of(2025, 9, 5), LocalTime.of(10, 0),
      AppointmentStatus.UPCOMING, AppointmentType.CONSULTATION));

    history.add(createAppointment(4L, "Dr. Weasley", "General Medicine",
      LocalDate.of(2025, 8, 27), LocalTime.of(10, 0),
      AppointmentStatus.UPCOMING, AppointmentType.FOLLOW_UP));

    // Add completed appointments
    history.add(createAppointment(5L, "Oral Nurse", "Dentistry",
      LocalDate.of(2025, 6, 5), LocalTime.of(9, 0),
      AppointmentStatus.COMPLETED, AppointmentType.VITAL_CHECK));

    return history;
  }

  /**
   * Helper method to create appointment objects
   */
  private Appointment createAppointment(Long id, String doctorName, String department,
                                        LocalDate date, LocalTime time,
                                        AppointmentStatus status, AppointmentType type) {
    Appointment appointment = new Appointment();
    appointment.setId(id);
    appointment.setPatientId(1L); // Currently using fixed patient ID
    appointment.setDoctorName(doctorName);
    appointment.setDepartment(department);
    appointment.setDate(date);
    appointment.setTime(time);
    appointment.setStatus(status);
    appointment.setType(type);
    return appointment;
  }

  /**
   * Simulate appointment cancellation
   * TODO: Replace with actual database update logic
   */
  private boolean simulateCancelAppointment(Long appointmentId) {
    // Simulate cancellation logic
    if (appointmentId != null && appointmentId > 0) {
      return true; // Simulate success
    }
    return false;
  }
}
