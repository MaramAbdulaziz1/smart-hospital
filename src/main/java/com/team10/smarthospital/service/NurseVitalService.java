package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.NurseVitalMapper;
import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.NurseVital;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.VitalsRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NurseVitalService {

    @Autowired private UserMapper userMapper;

    @Autowired private AppointmentMapper appointmentMapper;

    @Autowired private NurseVitalMapper nurseVitalMapper;

    public BaseResponse<Void> add(String email, NurseVital nurseVital) {
        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail("", "email can not be empty", null);
        }
        User nurse = userMapper.getUserByEmail(email);
        if (nurse == null) {
            return BaseResponse.fail("", "User not found or invalid role", null);
        }
        String appointmentId = nurseVital.getAppointmentId();
        Appointment appointment = appointmentMapper.getAppointmentsById(appointmentId);
        if (appointment.getProviderId().equals(nurse.getUserId())) {
            nurseVital.setNurseName(nurse.getFullName());
            nurseVitalMapper.insert(nurseVital);
            return BaseResponse.success("");
        } else {
            return BaseResponse.fail("401", "forbidden");
        }
    }

  public List<VitalsRecord> getVitalsByAppointmentId(String appointmentId) {
        Appointment appointment = appointmentMapper.getAppointmentsById(appointmentId);
        List<Appointment> appointments =
                appointmentMapper.getAppointmentsByPatientId(appointment.getPatientId());
        List<String> appointmentIds = new ArrayList<>();
        for (Appointment appointment1 : appointments) {
            appointmentIds.add(appointment1.getAppointmentId());
        }
        List<NurseVital> nurseVitals = nurseVitalMapper.getByAppointmentIds(appointmentIds);
        return toVitalsRecords(nurseVitals);
    }

    private List<VitalsRecord> toVitalsRecords(List<NurseVital> nurseVitals) {
        List<VitalsRecord> vitalsRecords = new ArrayList<>();
        if (!nurseVitals.isEmpty()) {
            for (NurseVital nurseVital : nurseVitals) {
                VitalsRecord vitalsRecord = new VitalsRecord();
                vitalsRecord.setDate(nurseVital.getUpdateTime().toLocalDate());
                vitalsRecord.setBp(nurseVital.getBloodPressure());
                vitalsRecord.setHeartRate(nurseVital.getHeartRate());
                vitalsRecord.setTemperature(nurseVital.getTemperature());
                vitalsRecord.setWeight(nurseVital.getWeight());
                vitalsRecord.setHeight(nurseVital.getHeight());
                vitalsRecord.setBmi(nurseVital.getBmi());
                vitalsRecord.setGlucose(nurseVital.getGlucose());
                vitalsRecord.setO2Saturation(nurseVital.getO2Saturation());
                vitalsRecord.setRespiratoryRate(nurseVital.getRespiratoryRate());
                vitalsRecord.setNurseName(nurseVital.getNurseName());
                vitalsRecords.add(vitalsRecord);
            }
        }
        return vitalsRecords;
    }
}
