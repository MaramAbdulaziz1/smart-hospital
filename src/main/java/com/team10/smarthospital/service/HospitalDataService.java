package com.team10.smarthospital.service;

import org.springframework.stereotype.Service;
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
}