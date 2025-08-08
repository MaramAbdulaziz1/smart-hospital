package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
public class IntakeController {
   //TODO: testing home page
   /*@GetMapping("/")
   public String home() {
      return "patientintake"; // to load homePage.html from templates folder
   }*/

   @GetMapping("/patientintake")
   public String IntakePage(Model model){
      model.addAttribute("pageTitle", "Patient Intake");
      return "patientintake";
   }

}
