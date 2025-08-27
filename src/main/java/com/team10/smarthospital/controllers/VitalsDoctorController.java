package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.Vitals;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VitalsDoctorController {

    @GetMapping("/vitals-doctor")
    public String showVitalsPage(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Vitals");
        // Add current time for display
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        model.addAttribute("currentTime", now.format(formatter));

        return "vitalsDoctor";
    }
}
