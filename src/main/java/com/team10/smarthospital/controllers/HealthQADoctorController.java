package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthQADoctorController {

  @GetMapping("/HealthQADoctor")
  public String healthQADoctor(Model model) {
    model.addAttribute("pageTitle", "Health Q&A");
    return "healthQADoctor";
  }
}
