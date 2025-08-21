package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.GenderView;
import com.team10.smarthospital.model.PatientProfileView;
import com.team10.smarthospital.model.VisitRecord;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import java.lang.reflect.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientProfileController {

  @GetMapping("/patientProfile")
  public String patientProfilePage(Model model) {
    // Create mock patient data
    PatientProfileView patient = new PatientProfileView();
    patient.setName("Harry Potter");
    patient.setEmail("harrypotter2000@gmail.com");
    patient.setPhone("+44 07458545663");
    patient.setSex(GenderView.MALE);
    patient.setAge(30);
    patient.setDateOfBirth(LocalDate.of(1999, 8, 23));
    patient.setWeight("75 kg");
    patient.setHeight("178 cm");
    patient.setBloodType("A");
    patient.setAddress("Marlborough House, Bristol, BS1 3NX");

    // Fix: Set mock ID for testing (simulate database-generated ID)
    setMockId(patient, 17653L);

    // Create mock visit history
    List<VisitRecord> history = new ArrayList<>();
    history.add(new VisitRecord("10 Jul 2025", "General Medicine", "Viral Flu", "Dr. Sarah Moyo"));
    history.add(new VisitRecord("18 Jun 2024", "Dermatology", "Eczema", "Dr. Samuel Chen"));
    history.add(new VisitRecord("02 May 2024", "Cardiology", "Hypertension", "Dr. Linda Patel"));
    history.add(new VisitRecord("27 Apr 2023", "Neurology", "Migraine", "Dr. Emily Zhou"));

    // Add patient and history to model
    model.addAttribute("patient", patient);
    model.addAttribute("history", history);
    model.addAttribute("pageTitle", "Patient Profile");

    return "patientProfile";
  }

  // Helper method to set mock ID for testing
  private void setMockId(PatientProfileView patient, Long id) {
    try {
      // Use reflection to set the ID field in BaseEntityView
      Class<?> baseClass = patient.getClass();
      while (baseClass != null && !baseClass.getSimpleName().equals("BaseEntityView")) {
        baseClass = baseClass.getSuperclass();
      }

      if (baseClass != null) {
        Field idField = baseClass.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(patient, id);
      }
    } catch (Exception e) {
      System.err.println("Could not set mock ID: " + e.getMessage());
      // Fallback: you can manually create a simple getter
    }
  }
}

/*@Controller
public class PatientReportController {

    @GetMapping("/patient-report")
    public String showPatientReport(@RequestParam("id") Long patientId, Model model) {
        // Fetch patient data by ID
        Patient patient = patientService.getPatientById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }

        // Fetch patient report
        PatientReport patientReport = reportService.getPatientReportByPatientId(patientId);

        // Fetch dropdown items
        List<Item> items = reportService.getDropdownItems();

        // Add attributes to the model
        model.addAttribute("patient", patient);
        model.addAttribute("patientReport", patientReport);
        model.addAttribute("items", items);

        return "patient-report"; // Return the name of the Thymeleaf template
    }
}*/
