package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DepartmentResponse;
import com.team10.smarthospital.service.DoctorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired private DoctorService doctorService;

    @GetMapping("/department")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<DepartmentResponse>> getDoctorByDepartment(
            @RequestParam("department") Integer department) {
        return doctorService.getDoctorByDepartment(department);
    }

    @GetMapping("/appointment")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<AppointmentRecord>> getAppointmentTime(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("doctorId") String doctorId,
            @RequestParam("date") LocalDate date) {
        return doctorService.getAppointmentTime(userDetails.getUsername(), doctorId, date);
    }
}
