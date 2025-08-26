package com.team10.smarthospital.controllers;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrescriptionController {

    @GetMapping("/prescription")
    public String prescriptionPage(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Prescription");
        return "prescription";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "homePage";
    }

    @GetMapping("/patient-login")
    public String patientLoginPage() {
        return "homePage";
    }
}
