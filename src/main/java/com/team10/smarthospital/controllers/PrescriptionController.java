package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.request.PrescriptionAdd;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.PrescriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrescriptionController {

    @Autowired private PrescriptionService prescriptionService;

    @GetMapping("/prescription")
    public String prescriptionPage(
            @RequestParam(name = "appointmentId") String appointmentId, Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Patient Prescription");
        return "prescription";
    }

    @PostMapping("/prescription/add")
    @PreAuthorize("hasRole('DOCTOR')")
    public BaseResponse<Void> addPrescription(@RequestBody PrescriptionAdd prescriptionAdd) {
        return prescriptionService.addPrescription(prescriptionAdd);
    }
}
