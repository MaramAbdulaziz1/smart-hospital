package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VitalsController {

    @GetMapping("/vitals")
    public String showVitalsPage(Model model) {
        model.addAttribute("pageTitle", "Patient Vitals"); // Dynamic title
        return "vitals"; // View template name
    }
}
