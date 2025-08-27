package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VitalsHistoryPageController {

    @GetMapping("/vitalsHistoryPage")
    public String showVitalsHistory(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Vitals");
        return "vitalsHistoryPage";
    }
}
