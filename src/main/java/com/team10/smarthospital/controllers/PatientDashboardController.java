package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientDashboardController {

    @Autowired private AppointmentService appointmentService;

    @GetMapping("/patient/patientDashboard")
    public String showPatientDashboard(
            Model model, @AuthenticationPrincipal UserDetails userDetails) {
        BaseResponse<List<AppointmentRecord>> listBaseResponse =
                appointmentService.getPatientUpcoming(userDetails.getUsername());
        List<AppointmentRecord> appointmentRecords = new ArrayList<>();
        if (ResponseCode.SUCCESS.getCode().equals(listBaseResponse.getCode())) {
            appointmentRecords = listBaseResponse.getData();
        }
        model.addAttribute("appointmentRecords", appointmentRecords);
        return "patientdashboard";
    }
}
