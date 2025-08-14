package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

  @GetMapping("/dashboard")
  public String dashboardPage(Model model) {
    model.addAttribute("pageTitle", "Home");
    return "dashboard";
  }
}
