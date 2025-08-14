package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "homePage"; // templates/homePage.html
  }

  // Patient registration (keep these)
  @GetMapping({"/patient/registration", "/patientRegistration", "/patientRegistration.html"})
  public String patientRegister() {
    return "patientRegistration"; // templates/patientRegistration.html
  }
}
