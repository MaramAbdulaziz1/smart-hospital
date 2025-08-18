package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for patient-side Health Q&A functionality
 */
@Controller
public class HealthQAPatientController {

  @GetMapping("/healthQAPatient")
  public String healthQAPatient(Model model) {
    model.addAttribute("pageTitle", "Coming Soon");
    return "healthQAPatient"; // This refers to healthQAPatient.html in templates folder
  }
}
