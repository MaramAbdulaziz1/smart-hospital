package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientdashboardController {

    @GetMapping("/patient/patientDashboard")
    public String showPatientDashboard(Model model) {
        return "patientdashboard";
    }
}
