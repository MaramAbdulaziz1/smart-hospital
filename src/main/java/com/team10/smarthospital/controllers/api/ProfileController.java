package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.request.PatientProfileRequest;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DoctorProfileResponse;
import com.team10.smarthospital.model.response.NurseProfileResponse;
import com.team10.smarthospital.model.response.PatientProfileResponse;
import com.team10.smarthospital.service.DoctorProfileService;
import com.team10.smarthospital.service.NurseProfileService;
import com.team10.smarthospital.service.PatientProfileDocService;
import com.team10.smarthospital.service.PatientProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * description
 *
 * @author yfengl
 * @since 2025/8/15 20:26
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired private PatientProfileService patientProfileService;

    @Autowired private DoctorProfileService doctorProfileService;

    @Autowired private NurseProfileService nurseProfileService;

    @Autowired private PatientProfileDocService patientProfileDocService;

    @GetMapping("/patient")
    @PreAuthorize("hasAnyRole('DOCTOR', 'NURSE')")
    public BaseResponse<PatientProfileResponse> getPatientProfile(
            @RequestParam("appointmentId") String appointmentId) {
        return patientProfileDocService.getUserProfile(appointmentId);
    }

    @GetMapping("/me/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<PatientProfileResponse> getCurrentPatient(
            @AuthenticationPrincipal UserDetails userDetails) {
        return patientProfileService.getUserProfile(userDetails.getUsername());
    }

    @GetMapping("/me/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public BaseResponse<DoctorProfileResponse> getCurrentDoctor(
            @AuthenticationPrincipal UserDetails userDetails) {
        return doctorProfileService.getUserProfile(userDetails.getUsername());
    }

    @GetMapping("/me/nurse")
    @PreAuthorize("hasRole('NURSE')")
    public BaseResponse<NurseProfileResponse> getCurrentNurse(
            @AuthenticationPrincipal UserDetails userDetails) {
        return nurseProfileService.getUserProfile(userDetails.getUsername());
    }

    @PostMapping("/patient/update")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<Void> updatePatientProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PatientProfileRequest patientProfileRequest) {
        return patientProfileService.updatePatientProfile(
                userDetails.getUsername(), patientProfileRequest);
    }
}
