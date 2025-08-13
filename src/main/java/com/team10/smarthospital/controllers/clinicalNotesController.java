package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
public class clinicalNotesController {
   //TODO: testing home page
   /*@GetMapping("/")
   public String home() {
      return "patientintake"; // to load homePage.html from templates folder
   }*/

   @GetMapping("/clinicalNotes")
   public String IntakePage(Model model){
      model.addAttribute("pageTitle", "Clinical Notes");
      return "clinicalNotes";
   }

}
