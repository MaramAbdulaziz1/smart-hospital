package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.request.DoctorRegister;
import com.team10.smarthospital.model.request.NurseRegister;
import com.team10.smarthospital.model.request.PatientRegister;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.service.RegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/register")
public class UserRegisterController {

    @Autowired private RegisterService registerService;

    @PostMapping("/patient")
    public ResponseEntity<BaseResponse<User>> registerPatient(
            @RequestBody PatientRegister register) {
        return ResponseEntity.ok(registerService.register(register));
    }

    @PostMapping("/doctor")
    public ResponseEntity<BaseResponse<User>> registerDoctor(@RequestBody DoctorRegister register) {
        return ResponseEntity.ok(registerService.register(register));
    }

    @PostMapping("/nurse")
    public ResponseEntity<BaseResponse<User>> registerNurse(@RequestBody NurseRegister register) {
        return ResponseEntity.ok(registerService.register(register));
    }
}
