package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarPatientController {

    @GetMapping("/calendarPatient")
    public String showCalendarPage(Model model) {
        model.addAttribute("pageTitle", "Calendar");
        return "calendarPatient";
    }
}
