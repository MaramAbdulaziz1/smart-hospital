package com.team10.smarthospital.controllers;

  import java.time.LocalDate;
  import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.team10.smarthospital.model.PatientProfileView;
import com.team10.smarthospital.model.VisitRecord;
import com.team10.smarthospital.service.HospitalDataService;

  @Controller
  public class PatientReportController {

    @SuppressWarnings("unused")
    private final HospitalDataService hospitalDataService;

    public PatientReportController(HospitalDataService hospitalDataService) {
        this.hospitalDataService = hospitalDataService;
    }

    /**
     * Handles the request to display the patient report by date.
     *
     * @param model The model to hold attributes for the view.
     * @return The name of the view to render.
     */
    @GetMapping("/{patientId}/visits")
    public String showVisitRecordsPage(@PathVariable Long patientId, Model model) {



      List<String> visitDates = hospitalDataService.getVisitDatesByPatientId(patientId);

        model.addAttribute("patientId", patientId);
        model.addAttribute("visitDates", visitDates);
        model.addAttribute("selectedDate", null);
        model.addAttribute("records", List.of());

      return "patient-report"; // loads patient-report.html
    }

    // After selecting a date
    @GetMapping("/{patientId}/visits/records")
    public String getVisitRecordsByDate(@PathVariable Long patientId,
                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                        Model model) {
        List<String> visitDates = hospitalDataService.getVisitDatesByPatientId(patientId);
        List<VisitRecord> records = hospitalDataService.getRecordsByDate(patientId, date);

        model.addAttribute("patientId", patientId);
        model.addAttribute("visitDates", visitDates);
        model.addAttribute("selectedDate", date);
        model.addAttribute("records", records);

        return "patient-report";
    }


      /**
       * Handles the request to display the patient report.
       *
       * @param patientId The ID of the patient.
       * @param date The date of the visit.
       * @param model The model to hold attributes for the view.
       * @return The name of the view to render.
       */

      @GetMapping("/patient-report/{id}/{date}")
      public String getPatientReport(@PathVariable("id") Long patientId, @PathVariable("date") String date, Model model) {

      List<VisitRecord> visitRecords = new ArrayList<>();
      //get the visit record  for the given patientId along with visit dates from database , filter by requested data and popuate visitRecord object
      VisitRecord visitRecord = new VisitRecord();
      visitRecord.setPatientId(patientId); // Mock patient ID, in a real application this would be dynamic
      visitRecord.setPatientName("Harry Potter");
      visitRecord.setDateOfVisit(date); // Mock date, in a real application this would be dynamic
      visitRecord.setDepartment("Cardiology");
      visitRecord.setDiagnosis("Hypertension");
      visitRecord.setDoctorName("Dr. John Smith");
      visitRecord.setDoctorsNotes("Patient is recovering well. Continue with prescribed medication.");
      visitRecord.setTreatmentPlan(null);
      visitRecord.setAllergies(null);
      List <String> prescriptions = new ArrayList<>();
      prescriptions.add("Aspirin 100mg");
      prescriptions.add("Lisinopril 10mg");
      visitRecord.setCurrentMedications(prescriptions);
      visitRecord.setFamilyHistory(null);
      visitRecord.setSocialHistory(null);
      visitRecord.setPastSurgicalHistory(null);
      visitRecord.setPastMedications(null);
      visitRecord.setPastMedicalConditions(null);

      //PatientProfile object to be used in the template
      PatientProfileView patientProfile = new PatientProfileView();
      patientProfile.setId(patientId); // Mock ID, in a real application this would be dynamic
      patientProfile.setName("Harry Potter");
      visitRecords.add(visitRecord);
      // In a real application, you would fetch the visit record from the database using patientId and date

      model.addAttribute("visitRecord", visitRecord);
      model.addAttribute("patientProfile", patientProfile);

      return "patient-report"; // loads patient-report.html
      }
  }
