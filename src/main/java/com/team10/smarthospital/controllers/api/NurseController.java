package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DoctorDepartment;
import com.team10.smarthospital.service.NurseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/nurse")
public class NurseController {

    @Autowired private NurseService nurseService;

    @GetMapping("/department")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<DoctorDepartment>> getNurseByDepartment(
            @RequestParam("department") Integer department) {
        return nurseService.getNurseByDepartment(department);
    }

    @GetMapping("/appointment")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<AppointmentRecord>> getAppointmentTime(
            @RequestParam("nurseId") String nurseId, @RequestParam("date") LocalDate date) {
        return nurseService.getAppointmentTime(nurseId, date);
    }
}
