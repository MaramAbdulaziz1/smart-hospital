package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.response.VitalsRecord;
import com.team10.smarthospital.service.NurseVitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VitalsHistoryPageController {

    @Autowired private NurseVitalService nurseVitalService;

    @GetMapping("/vitalsHistoryPage")
    public String showVitalsHistory(
            @RequestParam(name = "appointmentId") String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Vitals");
        List<VitalsRecord> vitalsHistory =
                nurseVitalService.getVitalsByAppointmentId(appointmentId);
        model.addAttribute("vitalsHistory", vitalsHistory);
        return "vitalsHistoryPage";
    }
}
