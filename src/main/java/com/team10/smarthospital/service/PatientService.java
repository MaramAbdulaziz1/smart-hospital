package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.model.entity.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PatientService implements IUserService<Patient>{

    @Autowired private PatientMapper patientMapper;

    @Override
    public void insertUser(Patient patient) {
        patient.setPatientCode(generateUniquePatientCode());
        patientMapper.insertUser(patient);
    }

    private String generateUniquePatientCode() {
        String code;
        do {
            code = "P" + UUID.randomUUID().toString().replace("-", "").substring(0, 9).toUpperCase();
        } while (patientMapper.existsByPatientCode(code) == 1);
        return code;
    }
}
