package com.team10.smarthospital.service;

import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.request.NurseRegister;
import com.team10.smarthospital.model.request.PatientRegister;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegisterService {

    @Autowired private UserService userService;

    @Autowired private PatientService patientService;

    @Autowired private DoctorService doctorService;

    @Autowired private NurseService nurseService;

    @Transactional
    public BaseResponse<Void> register(PatientRegister register) {
        User userOld = userService.getUserByEmail(register.getEmail());
        if (userOld != null) {
            return BaseResponse.fail("", "This username has already been registered");
        }
        User user = this.getUserByRegister(register);
        user.setRole(Role.PATIENT.getRoleCode());
        userService.insertUser(user);
        register.setUserId(user.getUserId());
        patientService.insertUser(register);
        return BaseResponse.success("Register successful");
    }

    @Transactional
    public BaseResponse<Void> register(NurseRegister register) {
        User userOld = userService.getUserByEmail(register.getEmail());
        if (userOld != null) {
            return BaseResponse.fail("", "This username has already been registered");
        }
        User user = this.getUserByRegister(register);
        register.setUserId(user.getUserId());
        if (Role.NURSE.getRoleCode().equals(register.getRole())) {
            userService.insertUser(user);
            nurseService.insertUser(register);
        } else if (Role.DOCTOR.getRoleCode().equals(register.getRole())) {
            userService.insertUser(user);
            doctorService.insertUser(register);
        } else {
            return BaseResponse.fail("", "role error");
        }
        return BaseResponse.success("Register successful");
    }

    private User getUserByRegister(User register) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRole(register.getRole());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setBirth(register.getBirth());
        user.setGender(register.getGender());
        user.setEmail(register.getEmail());
        user.setPassword(register.getPassword());
        user.setMobileNumber(register.getMobileNumber());
        user.setAvatarBase64(register.getAvatarBase64());
        return user;
    }
}
