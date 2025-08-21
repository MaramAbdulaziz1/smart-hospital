package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.request.EmployeeRegister;
import com.team10.smarthospital.model.request.PatientRegister;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
public class UserRegisterController {

    @Autowired private RegisterService registerService;

    @PostMapping("/patient")
    public BaseResponse<Void> registerPatient(@RequestBody PatientRegister register) {
        return registerService.register(register);
    }

    @PostMapping("/employee")
    public BaseResponse<Void> registerDoctor(@RequestBody EmployeeRegister register) {
        return registerService.register(register);
    }
}
