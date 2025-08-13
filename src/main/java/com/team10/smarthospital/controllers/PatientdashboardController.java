package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientdashboardController {

    @GetMapping("/patientdashboard")
    public String showPatientDashboard() {
        // src/main/resources/templates/patientdashboard.html
        return "patientdashboard";
    }
}
