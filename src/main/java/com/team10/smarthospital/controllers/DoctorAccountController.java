package com.team10.smarthospital.controllers;

import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DoctorProfileResponse;
import com.team10.smarthospital.service.DoctorProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorAccountController {

    @Autowired private DoctorProfileService doctorProfileService;

    @GetMapping("/doctorAccount")
    public String accountPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        BaseResponse<DoctorProfileResponse> doctorProfile =
                doctorProfileService.getUserProfile(userDetails.getUsername());
        DoctorProfileResponse doctorProfileData = doctorProfile.getData();
        if (doctorProfileData == null) {
            doctorProfileData = new DoctorProfileResponse();
        }
        model.addAttribute("doctor", doctorProfileData);
        model.addAttribute("pageTitle", "My Profile");
        return "doctorAccount";
    }
}
