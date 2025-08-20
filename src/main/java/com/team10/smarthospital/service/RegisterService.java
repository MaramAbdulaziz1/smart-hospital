package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.DoctorMapper;
import com.team10.smarthospital.mapper.NurseMapper;
import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.request.DoctorRegister;
import com.team10.smarthospital.model.request.NurseRegister;
import com.team10.smarthospital.model.request.PatientRegister;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegisterService {

    @Autowired private UserMapper userMapper;

    @Autowired private PatientMapper patientMapper;

    @Autowired private DoctorMapper doctorMapper;

    @Autowired private NurseMapper nurseMapper;

    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public BaseResponse<User> register(PatientRegister register) {
        BaseResponse<User> response = new BaseResponse<>();
        User userOld = userMapper.getUserByEmail(register.getEmail());
        if (userOld != null) {
            response.setCode("");
            response.setMessage("This username has already been registered");
            return response;
        }
        try {
            User user = this.getUserByRegister(register);
            user.setRole(Role.PATIENT.getRoleCode());
            userMapper.insertUser(user);
            register.setUserId(user.getUserId());
            patientMapper.insertUser(register);
            response.setCode("0");
            response.setMessage("Register successful");
        } catch (Exception e) {
            response.setCode("");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Transactional
    public BaseResponse<User> register(DoctorRegister register) {
        BaseResponse<User> response = new BaseResponse<>();
        User userOld = userMapper.getUserByEmail(register.getEmail());
        if (userOld != null) {
            response.setCode("");
            response.setMessage("This username has already been registered");
            return response;
        }
        try {
            User user = this.getUserByRegister(register);
            user.setRole(Role.DOCTOR.getRoleCode());
            userMapper.insertUser(user);
            register.setUserId(user.getUserId());
            doctorMapper.insertUser(register);
            response.setCode("0");
            response.setMessage("Register successful");
        } catch (Exception e) {
            response.setCode("");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Transactional
    public BaseResponse<User> register(NurseRegister register) {
        BaseResponse<User> response = new BaseResponse<>();
        User userOld = userMapper.getUserByEmail(register.getEmail());
        if (userOld != null) {
            response.setCode("");
            response.setMessage("This username has already been registered");
            return response;
        }
        try {
            User user = this.getUserByRegister(register);
            user.setRole(Role.NURSE.getRoleCode());
            userMapper.insertUser(user);
            register.setUserId(user.getUserId());
            nurseMapper.insertUser(register);
            response.setCode("0");
            response.setMessage("Register successful");
        } catch (Exception e) {
            response.setCode("");
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private User getUserByRegister(User register) {
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setGender(register.getGender());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setMobileNumber(register.getMobileNumber());
        user.setAvatarUrl(register.getAvatarUrl());
        return user;
    }
}
