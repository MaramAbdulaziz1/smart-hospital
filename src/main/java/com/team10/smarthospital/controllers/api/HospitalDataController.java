package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.Department;
import com.team10.smarthospital.service.HospitalDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class HospitalDataController {

    @Autowired private HospitalDataService hospitalDataService;

    @GetMapping("/department")
    @PreAuthorize("hasRole('PATIENT')")
    public BaseResponse<List<Department>> getAllDepartment() {
        return hospitalDataService.getAllDepartment();
    }
}
