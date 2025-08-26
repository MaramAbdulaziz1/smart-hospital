package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.entity.PatientIntake;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.IntakeHistory;
import com.team10.smarthospital.service.PatientIntakeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intake")
public class PatientIntakeController {

    @Autowired private PatientIntakeService patientIntakeService;

    @GetMapping("/history")
    public BaseResponse<List<IntakeHistory>> getPatientHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("appointmentId") String appointmentId) {
        return patientIntakeService.getPatientHistory(userDetails.getUsername(), appointmentId);
    }

    @PostMapping("/add")
    public BaseResponse<String> add(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody PatientIntake intake) {
        return patientIntakeService.add(userDetails.getUsername(), intake);
    }
}
