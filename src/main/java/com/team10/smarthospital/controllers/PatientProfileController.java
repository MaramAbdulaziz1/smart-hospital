package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientProfileController {

    @GetMapping("/patientProfile")
    public String patientProfilePage(
            Model model, @RequestParam(name = "email", required = false) String email) {
        model.addAttribute("email", email);
        model.addAttribute("pageTitle", "Patient Profile");
        return "patientProfile";
    }
}
