package com.team10.smarthospital.service;

import org.springframework.stereotype.Service;

import com.team10.smarthospital.model.VisitRecord;

import java.time.LocalDate;
import java.util.*;

/**
 * Service for managing hospital static data
 * Provides department, doctor, nurse, and time options
 */

@Service
public class HospitalDataService {

    // department
    public List<String> getAllDepartments() {
        return Arrays.asList(
            "General Medicine",
            "General Surgery",
            "Cardiology",
            "Nephrology",
            "Pediatrics"
        );
    }

    // doctors based on department
    public List<String> getDoctorsByDepartment(String department) {
        Map<String, List<String>> departmentDoctors = new HashMap<>();

        departmentDoctors.put("General Medicine", Arrays.asList("Arthur Weasley", "James Carterfield"));
        departmentDoctors.put("General Surgery", Arrays.asList("Maram Fennelly", "Edward Bristow"));
        departmentDoctors.put("Cardiology", Arrays.asList("Thomas Ellington", "Samuel Fenwick"));
        departmentDoctors.put("Nephrology", Arrays.asList("Samuel Fenwick", "Henry Brambleton"));
        departmentDoctors.put("Pediatrics", Arrays.asList("Charles Ashworth", "Adrian Hallowby"));

        return departmentDoctors.getOrDefault(department, new ArrayList<>());
    }

    // nurse based on department
    public String getNurseByDepartment(String department) {
        Map<String, String> departmentNurses = new HashMap<>();

        departmentNurses.put("General Medicine", "Riley Granger");
        departmentNurses.put("General Surgery", "Leona Sterling");
        departmentNurses.put("Cardiology", "Emily Hargrave");
        departmentNurses.put("Nephrology", "Anna Moonridge");
        departmentNurses.put("Pediatrics", "Victor Loxley");

        return departmentNurses.get(department);
    }

    // time
    public List<String> getAvailableTimes() {
        return Arrays.asList(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30",
            "16:00", "16:30"
        );
    }
    //get patient visits by patient ID
    public List<VisitRecord> getPatientVisitsByPatientId(Long patientId){
       //get the visit record for the given date from database and popuate visitRecord object
      //TO_DO: Implement database logic to fetch visit records by date
      // For demonstration purposes, we will create a mock VisitRecord object
      // In a real application, you would fetch the visit record from the database using the date parameter

        // For now, we will return an empty list as a placeholder.
      List<VisitRecord> patientVisits = new ArrayList<>();
      VisitRecord visitRecord = new VisitRecord();
      visitRecord.setPatientId(patientId); // Mock patient ID, in a real application this would be dynamic
      visitRecord.setPatientName("Harry Potter");
      visitRecord.setDateOfVisit(LocalDate.now().toString()); // Mock date, in a real application this would be dynamic
      visitRecord.setDepartment("Cardiology");
      visitRecord.setDiagnosis("Hypertension");
      visitRecord.setDoctorName("Dr. John Smith");
      visitRecord.setDoctorsNotes("Patient is recovering well. Continue with prescribed medication.");
      visitRecord.setTreatmentPlan(null);
      visitRecord.setPastMedicalConditions(null);

      visitRecord.setAllergies(null);
      visitRecord.setCurrentMedications(null);
      visitRecord.setFamilyHistory(null);
      visitRecord.setSocialHistory(null);
      visitRecord.setPastSurgicalHistory(null);
      visitRecord.setPastMedications(null);
      visitRecord.setPastMedicalConditions(null);

      patientVisits.add(visitRecord);
      return patientVisits;
    }
    public List<String> getVisitDatesByPatientId(Long patientId) {
        return getPatientVisitsByPatientId(patientId).stream()
                .map(VisitRecord::getDateOfVisit)
                .distinct()
                .sorted()
                .toList();
    }

    public List<VisitRecord> getRecordsByDate(Long patientId, LocalDate date) {
        return getPatientVisitsByPatientId(patientId).stream()
                .filter(r -> r.getDateOfVisit().equals(date))
                .toList();
    }
}