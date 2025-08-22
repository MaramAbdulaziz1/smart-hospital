package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.model.entity.Patient;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.request.PatientProfileRequest;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientService implements IUserService<Patient> {

    @Autowired private PatientMapper patientMapper;

    @Autowired private UserService userService;

    @Override
    public void insertUser(Patient patient) {
        patient.setPatientCode(generateUniquePatientCode());
        patientMapper.insertUser(patient);
    }

    private String generateUniquePatientCode() {
        String code;
        do {
            code =
                    "P"
                            + UUID.randomUUID()
                                    .toString()
                                    .replace("-", "")
                                    .substring(0, 9)
                                    .toUpperCase();
        } while (patientMapper.existsByPatientCode(code) == 1);
        return code;
    }

    public BaseResponse<Void> updatePatientProfile(
            String email, PatientProfileRequest patientProfileRequest) {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (!ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            return BaseResponse.fail(userBaseResponse.getCode(), userBaseResponse.getMessage());
        }
        String patientId = userBaseResponse.getData().getUserId();
        patientProfileRequest.setPatientId(patientId);
        patientMapper.updatePatientProfile(patientProfileRequest);
        return BaseResponse.success(null);
    }
}
