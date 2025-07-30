package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class VitalsHistoryPage {

  @GetMapping("/vitalsHistoryPage")
  public String showVitalsHistory(Model model) {
    model.addAttribute("pageTitle", "Patient Vitals");
    model.addAttribute("pageContent", "vitalsHistoryPage :: content");
    return "layouts/sharedLayout";
  }
}
