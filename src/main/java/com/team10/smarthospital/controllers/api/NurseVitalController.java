package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.entity.NurseVital;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.VitalsRecord;
import com.team10.smarthospital.service.NurseVitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vital")
public class NurseVitalController {

    @Autowired private NurseVitalService nurseVitalService;

    @PostMapping("/add")
    public BaseResponse<Void> add(
            @AuthenticationPrincipal UserDetails userDetails, @RequestBody NurseVital nurseVital) {
        return nurseVitalService.add(userDetails.getUsername(), nurseVital);
    }

    @GetMapping("/patient")
    public BaseResponse<List<VitalsRecord>> getVital(
            @RequestParam("appointmentId") String appointmentId) {
        return BaseResponse.success(
                null, nurseVitalService.getVitalsByAppointmentId(appointmentId));
    }
}
