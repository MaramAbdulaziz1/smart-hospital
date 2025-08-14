package com.team10.smarthospital.controllers;

  import org.springframework.stereotype.Controller;
  import org.springframework.ui.Model;
  import org.springframework.web.bind.annotation.GetMapping;

  import com.team10.smarthospital.model.VisitRecord;

  @Controller
  public class PatientReportController {

      @GetMapping("/patient-report")

      //public String viewPatientReport(@RequestParam("patientId") String patientId, @RequestParam("date") String date, Model model) {
      public String viewPatientReport(Model model) {

       /*  // Mock data for demonstration purposes
          String doctorsNotes = "Patient is recovering well. Continue with prescribed medication.";
          String treatmentPlan = "1. Continue antibiotics for 7 days.\n2. Schedule follow-up in 2 weeks.";
          String patientId = "12345"; // Mock patient ID
          // Mock vitals data
          String heartRate = "72";
          String bloodPressure = "120/80";
          String height = "175";
          String weight = "70";
          String temperature = "98.6";
        */

        // Add data to the model

         /*   model.addAttribute("doctorsNotes", doctorsNotes);
          model.addAttribute("treatmentPlan", treatmentPlan);
          model.addAttribute("heartRate", heartRate);
          model.addAttribute("bloodPressure", bloodPressure);
          model.addAttribute("height", height);
          model.addAttribute("weight", weight);
          model.addAttribute("temperature", temperature);
        */
        VisitRecord visitRecord = new VisitRecord();

        visitRecord.setDate("10 Jul 2025");
       // visitRecord.setDate(date); // this is sent to database along w/ patientId to get the patient record
        //visitRecord.setPatientId(patientId); // input from previous page
        // following field will be assigned based on info gathered from the top two database search parameters
        visitRecord.setDepartment("Cardiology");
        visitRecord.setDiagnosis("Hypertension");
        visitRecord.setDoctor("Dr. John Smith");
        visitRecord.setDoctorsNotes("Patient is recovering well. Continue with prescribed medication.");
        visitRecord.setTreatmentPlan("1. Continue antibiotics for 7 days.  2. Schedule follow-up in 2 weeks.");

        visitRecord.setHeartRate("72");
        visitRecord.setBloodPressure("120/80");
        visitRecord.setHeight("175");
        visitRecord.setWeight("70");
        visitRecord.setTemperature("98.6");

        model.addAttribute("visitRecord", visitRecord);
        return "patient-report"; // loads patient-report.html
        //return "visitRecord";
      }
  }
