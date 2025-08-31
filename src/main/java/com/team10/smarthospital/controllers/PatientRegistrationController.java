package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientRegistrationController {

    @GetMapping("/patient/registration")
    public String patientRegistrationPage(Model model) {
        model.addAttribute("pageTitle", "Patient Registration");
        return "patientRegistration";
    }
}
