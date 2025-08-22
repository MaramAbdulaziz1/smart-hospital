package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Patient;
import com.team10.smarthospital.model.request.PatientProfileRequest;

public interface PatientMapper extends BaseUserMapper<Patient> {

    int existsByPatientCode(String patientCode);

    void updatePatientProfile(PatientProfileRequest patientProfileRequest);
}
