package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.AppointmentView;
import com.team10.smarthospital.model.AppointmentStatusView;
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
    List<AppointmentView> upcomingAppointmentViews = createMockUpcomingAppointments();

    // Create mock appointment history data
    List<AppointmentView> appointmentViewHistory = createMockAppointmentHistory();

    // Add data to model for template rendering
    model.addAttribute("upcomingAppointmentViews", upcomingAppointmentViews);
    model.addAttribute("appointmentViewHistory", appointmentViewHistory);
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
        response.put("message", "AppointmentView cancelled successfully");
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
      List<AppointmentView> upcomingAppointmentViews = createMockUpcomingAppointments();
      List<AppointmentView> appointmentViewHistory = createMockAppointmentHistory();

      response.put("success", true);
      response.put("upcomingAppointmentViews", upcomingAppointmentViews);
      response.put("appointmentViewHistory", appointmentViewHistory);

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
  private List<AppointmentView> createMockUpcomingAppointments() {
    List<AppointmentView> appointmentViews = new ArrayList<>();

    // AppointmentView 1
    AppointmentView appointmentView1 = new AppointmentView();
    appointmentView1.setId(1L);
    appointmentView1.setPatientId(1L);
    appointmentView1.setDoctorName("Oral Nurse");
    appointmentView1.setDepartment("Dentistry");
    appointmentView1.setDate(LocalDate.of(2025, 9, 5));
    appointmentView1.setTime(LocalTime.of(10, 0));
    appointmentView1.setStatus(AppointmentStatusView.UPCOMING);
    appointmentView1.setType(AppointmentType.CONSULTATION);
    appointmentViews.add(appointmentView1);

    // AppointmentView 2
    AppointmentView appointmentView2 = new AppointmentView();
    appointmentView2.setId(2L);
    appointmentView2.setPatientId(1L);
    appointmentView2.setDoctorName("Dr. Weasley");
    appointmentView2.setDepartment("General Medicine");
    appointmentView2.setDate(LocalDate.of(2025, 8, 27));
    appointmentView2.setTime(LocalTime.of(10, 0));
    appointmentView2.setStatus(AppointmentStatusView.UPCOMING);
    appointmentView2.setType(AppointmentType.FOLLOW_UP);
    appointmentViews.add(appointmentView2);

    return appointmentViews;
  }

  /**
   * Create mock appointment history for testing
   * TODO: Replace with repository calls when connecting to database
   */
  private List<AppointmentView> createMockAppointmentHistory() {
    List<AppointmentView> history = new ArrayList<>();

    // Add upcoming appointments to history
    history.add(createAppointment(3L, "Oral Nurse", "Dentistry",
      LocalDate.of(2025, 9, 5), LocalTime.of(10, 0),
      AppointmentStatusView.UPCOMING, AppointmentType.CONSULTATION));

    history.add(createAppointment(4L, "Dr. Weasley", "General Medicine",
      LocalDate.of(2025, 8, 27), LocalTime.of(10, 0),
      AppointmentStatusView.UPCOMING, AppointmentType.FOLLOW_UP));

    // Add completed appointments
    history.add(createAppointment(5L, "Oral Nurse", "Dentistry",
      LocalDate.of(2025, 6, 5), LocalTime.of(9, 0),
      AppointmentStatusView.COMPLETED, AppointmentType.VITAL_CHECK));

    return history;
  }

  /**
   * Helper method to create appointment objects
   */
  private AppointmentView createAppointment(Long id, String doctorName, String department,
                                            LocalDate date, LocalTime time,
                                            AppointmentStatusView status, AppointmentType type) {
    AppointmentView appointmentView = new AppointmentView();
    appointmentView.setId(id);
    appointmentView.setPatientId(1L); // Currently using fixed patient ID
    appointmentView.setDoctorName(doctorName);
    appointmentView.setDepartment(department);
    appointmentView.setDate(date);
    appointmentView.setTime(time);
    appointmentView.setStatus(status);
    appointmentView.setType(type);
    return appointmentView;
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
