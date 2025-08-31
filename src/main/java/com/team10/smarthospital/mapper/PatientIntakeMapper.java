package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.PatientIntake;
import com.team10.smarthospital.model.response.IntakeHistory;

import java.util.List;

public interface PatientIntakeMapper {
    void insert(PatientIntake intake);

    List<PatientIntake> getByPatientIdAndAppointmentStatus(String patientId, Integer status);

    List<IntakeHistory> getIntakeHistoryByPatientId(String patientId);

    PatientIntake getByAppointmentId(String appointmentId);

    void update(PatientIntake intake);

    PatientIntake getByIntakeId(String intakeId);
}
