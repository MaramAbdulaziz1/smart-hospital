package com.team10.smarthospital.controllers;

import com.team10.smarthospital.service.HospitalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PatientdashboardController {
    
    @Autowired
    private HospitalDataService hospitalDataService;

    @GetMapping("/patient/patientDashboard")
    public String showPatientDashboard(Model model) {
        // sent data to front-end
        model.addAttribute("departments", hospitalDataService.getAllDepartments());
        model.addAttribute("availableTimes", hospitalDataService.getAvailableTimes());
        // src/main/resources/templates/patientdashboard.html
        return "patientdashboard";
    }

    // AJAX: choose department then loading doctor
    @GetMapping("/patient/getDoctors")
    @ResponseBody
    public List<String> getDoctorsByDepartment(@RequestParam String department) {
        return hospitalDataService.getDoctorsByDepartment(department);
    }

    // AJAX: choose department then loading nurse
    @GetMapping("/patient/getNurse")
    @ResponseBody
    public String getNurseByDepartment(@RequestParam String department) {
        return hospitalDataService.getNurseByDepartment(department);
    }

    // appointments
    @PostMapping("/patient/appointments/create")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createAppointment(
            @RequestParam String department,
            @RequestParam String doctor,
            @RequestParam String dateStr,
            @RequestParam String timeStr) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Appointment successful!");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Appointment failed：" + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Vital appointments
    @PostMapping("/patient/appointments/create-vital")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createVitalAppointment(
            @RequestParam String department,
            @RequestParam String nurse,
            @RequestParam String dateStr,
            @RequestParam String timeStr) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            
            response.put("success", true);
            response.put("message", "Appointment successful!");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Appointment failed：" + e.getMessage());
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}