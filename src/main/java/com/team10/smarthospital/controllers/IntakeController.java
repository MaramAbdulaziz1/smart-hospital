package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class IntakeController {
   //TODO: testing home page
   /*@GetMapping("/")
   public String home() {
      return "patientintake"; // to load homePage.html from templates folder
   }*/

   @GetMapping("/patientintake")
   public String IntakePage(){
      return "patientintake";
   }

}
