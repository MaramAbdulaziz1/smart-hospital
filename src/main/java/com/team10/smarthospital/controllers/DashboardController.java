package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

  @GetMapping("/dashboard")
  public String registerPage() {
    return "dashboard";  // loads register.html
  }
}
