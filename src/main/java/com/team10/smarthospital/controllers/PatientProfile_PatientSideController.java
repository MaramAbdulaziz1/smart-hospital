package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PatientProfile_PatientSideController {

  @GetMapping("/patientProfile-patientSide")
  public String patientProfile(Model model) {
    model.addAttribute("pageTitle", "My Profile");
    return "patientProfile-patientSide"; // This refers to patientProfile-patientSide.html in templates folder
  }
}
