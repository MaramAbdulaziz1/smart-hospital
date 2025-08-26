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
public class VitalsController {

    // In-memory storage for demo purposes (in real app, this would be a database)
    private static final List<Vitals> vitalsHistory = new ArrayList<>();

    @GetMapping("/vitals")
    public String showVitalsPage(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Vitals");
        // Add current time for display
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        model.addAttribute("currentTime", now.format(formatter));

        return "vitals";
    }

    @PostMapping("/vitals/save")
    @ResponseBody
    public String saveVitals(
            @RequestParam("heartRate") String heartRate,
            @RequestParam("bloodPressure") String bloodPressure,
            @RequestParam("temperature") String temperature,
            @RequestParam("weight") String weight,
            @RequestParam("height") String height,
            @RequestParam("bmi") String bmi,
            @RequestParam("bloodGlucose") String bloodGlucose,
            @RequestParam("oxygenSaturation") String oxygenSaturation,
            @RequestParam("respiratoryRate") String respiratoryRate) {

        try {
            // Create new vitals record
            Vitals vitals = new Vitals("HAT17653D", "Harry Potter", "Dr. Sarah Moyo");

            // Parse and set values (handle empty strings)
            vitals.setHeartRate(parseDoubleOrNull(heartRate));
            vitals.setBloodPressure(bloodPressure.isEmpty() ? null : bloodPressure);
            vitals.setTemperature(parseDoubleOrNull(temperature));
            vitals.setWeight(parseDoubleOrNull(weight));
            vitals.setHeight(parseDoubleOrNull(height));
            vitals.setBmi(parseDoubleOrNull(bmi));
            vitals.setBloodGlucose(parseDoubleOrNull(bloodGlucose));
            vitals.setOxygenSaturation(parseDoubleOrNull(oxygenSaturation));
            vitals.setRespiratoryRate(parseDoubleOrNull(respiratoryRate));

            // Add to history
            vitalsHistory.add(vitals);

            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    private Double parseDoubleOrNull(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
