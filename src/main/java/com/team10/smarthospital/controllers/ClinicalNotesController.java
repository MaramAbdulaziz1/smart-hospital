package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.IntakeHistory;
import com.team10.smarthospital.model.response.PatientProfileResponse;
import com.team10.smarthospital.service.PatientIntakeService;
import com.team10.smarthospital.service.PatientProfileDocService;
import com.team10.smarthospital.service.PatientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClinicalNotesController {

    @Autowired private PatientProfileDocService patientProfileDocService;

    @Autowired private PatientIntakeService patientIntakeService;

    @GetMapping("/clinicalNotes")
    public String IntakePage(
            @RequestParam(name = "appointmentId", required = true) String appointmentId,
            Model model) {
        model.addAttribute("appointmentId", appointmentId);
        model.addAttribute("pageTitle", "Clinical Notes");
        BaseResponse<PatientProfileResponse> response =
                patientProfileDocService.getUserProfile(appointmentId);
        String patientComplaint = null;
        if (ResponseCode.SUCCESS.getCode().equals(response.getCode())) {
            patientComplaint = response.getData().getChiefComplaint();
        }
        model.addAttribute("patientComplaint", patientComplaint);
        BaseResponse<IntakeHistory> intakeHistoryBaseResponse =
                patientIntakeService.getPatientIntake(appointmentId);
        IntakeHistory intake = null;
        if (ResponseCode.SUCCESS.getCode().equals(intakeHistoryBaseResponse.getCode())) {
            intake = intakeHistoryBaseResponse.getData();
        }
        model.addAttribute("intake", intake);
        return "clinicalNotes";
    }
}
