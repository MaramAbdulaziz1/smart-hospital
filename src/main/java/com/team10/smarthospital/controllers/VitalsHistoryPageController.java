package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class VitalsHistoryPageController {

  /**
   * Handles GET request to /vitalsHistoryPage
   * Loads the view and passes a dynamic title to shared layout
   */
  @GetMapping("/vitalsHistoryPage")
  public String showVitalsHistory(Model model) {
    model.addAttribute("pageTitle", "Patient Vitals"); // Dynamic title
    return "vitalsHistoryPage"; // View template name
  }
}
