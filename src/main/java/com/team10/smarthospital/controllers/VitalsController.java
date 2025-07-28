package com.team10.smarthospital.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VitalsController {

    @GetMapping("/vitals")
    public String vitalsPage() {
        return "vitals";
    }

    @GetMapping("/Vitals")
    public String vitalsPageUpperCase() {
        return "vitals";
    }
}
