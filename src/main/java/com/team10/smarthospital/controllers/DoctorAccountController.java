package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class DoctorAccountController {

  @GetMapping("/doctorAccount")
  public String accountPage(Model model) {

    model.addAttribute("pageTitle", "My Profile");
    return "doctorAccount";
  }

  @PostMapping("/account/update")
  public String updateAccount(
    @RequestParam String firstName,
    @RequestParam String lastName,
    @RequestParam String email,
    @RequestParam String phoneNumber,
    @RequestParam String gender,
    Model model) {

    // Add update logic in here
    // for example：use service update database

    // readd page title
    model.addAttribute("pageTitle", "Account Settings");

    // Add Success news
    model.addAttribute("message", "Account updated successfully!");
    return "redirect:/account";
  }
}
