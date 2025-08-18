package com.team10.smarthospital.controllers;

  import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team10.smarthospital.model.PatientProfile;
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
     * @param date The date of the visit.
     * @param model The model to hold attributes for the view.
     * @return The name of the view to render.
     */
    @GetMapping("/patient/{id}/visits")
    public String getVisitsByPatientId(@PathVariable("id") String patientId, Model model) {



      // In a real application, you would fetch the visit record from the database
      ArrayList<VisitRecord> visitRecords = (ArrayList<VisitRecord>) hospitalDataService.getPatientVisitsByPatientId("HAT17653D");
System.out.println("0 visitRecords size: " + visitRecords.size());
System.out.println("Date of visit value " + visitRecords.get(0).getDateOfVisit());
      model.addAttribute("visitRecords", visitRecords);

      return "patient-report"; // loads patient-report.html
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
      public String getPatientReport(@PathVariable("id") String patientId, @PathVariable("date") String date, Model model) {

      List<VisitRecord> visitRecords = new ArrayList<>();
      //get the visit record  for the given patientId along with visit dates from database , filter by requested data and popuate visitRecord object
      VisitRecord visitRecord = new VisitRecord();
      visitRecord.setPatientId("HAT17653D"); // Mock patient ID, in a real application this would be dynamic
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
      PatientProfile patientProfile = new PatientProfile();
      patientProfile.setId(1L); // Mock ID, in a real application this would be dynamic
      patientProfile.setName("Harry Potter");
      visitRecords.add(visitRecord);
      // In a real application, you would fetch the visit record from the database using patientId and date

      model.addAttribute("visitRecord", visitRecord);
      model.addAttribute("patientProfile", patientProfile);

      return "patient-report"; // loads patient-report.html
      }
  }
