package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClinicalNotesController {

    @GetMapping("/clinicalNotes")
    public String IntakePage(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Clinical Notes");
        return "clinicalNotes";
    }
}
