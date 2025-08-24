package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.PatientIntakeMapper;
import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.PatientIntake;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.IntakeHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientIntakeService {

    @Autowired private UserMapper userMapper;

    @Autowired private AppointmentMapper appointmentMapper;

    @Autowired private PatientIntakeMapper patientIntakeMapper;

    public BaseResponse<List<IntakeHistory>> getPatientHistory(String email, String patientEmail) {
        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail("", "email can not be empty", null);
        }
        User doctor = userMapper.getUserByEmail(email);
        if (doctor == null || !Role.DOCTOR.getRoleCode().equals(doctor.getRole())) {
            return BaseResponse.fail("", "User not found or invalid role", null);
        }
        User patient = userMapper.getUserByEmail(patientEmail);
        if (patient == null || !Role.PATIENT.getRoleCode().equals(patient.getRole())) {
            return BaseResponse.fail("", "User not found or invalid role", null);
        }
        List<PatientIntake> intakes = patientIntakeMapper.getByPatientId(patient.getUserId());
        return BaseResponse.success("", toHistory(intakes));
    }

    public BaseResponse<String> add(String email, PatientIntake intake) {
        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail("", "email can not be empty", null);
        }
        User doctor = userMapper.getUserByEmail(email);
        if (doctor == null || !Role.DOCTOR.getRoleCode().equals(doctor.getRole())) {
            return BaseResponse.fail("", "User not found or invalid role", null);
        }
        Appointment appointment = appointmentMapper.getAppointmentsById(intake.getAppointmentId());
        if (appointment != null && appointment.getProviderId().equals(doctor.getUserId())) {
            patientIntakeMapper.insert(intake);
            return BaseResponse.success("", null);
        } else {
            return BaseResponse.fail("", "", null);
        }
    }

    private List<IntakeHistory> toHistory(List<PatientIntake> intakes) {
        List<IntakeHistory> histories = new ArrayList<>();
        if (!intakes.isEmpty()) {
            for (PatientIntake intake : intakes) {
                IntakeHistory history = new IntakeHistory();
                history.setIntakeId(intake.getIntakeId());
                history.setAppointmentId(intake.getAppointmentId());
                history.setComplaint(intake.getComplaint());
                history.setNotes(intake.getNotes());
                history.setDiagnosis(intake.getDiagnosis());
                history.setFollowUpPlan(intake.getFollowUpPlan());
                histories.add(history);
            }
        }
        return histories;
    }
}
