package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.AppointTime;
import com.team10.smarthospital.model.enums.AppointmentStatus;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentSearchService {

    @Autowired private UserService userService;

    @Autowired private AppointmentMapper appointmentMapper;

    public BaseResponse<List<AppointmentRecord>> searchAppointment(String email, String search) {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (!ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            return BaseResponse.fail(
                    userBaseResponse.getCode(), userBaseResponse.getMessage(), null);
        }
        User user = userBaseResponse.getData();
        if (user == null || Role.PATIENT.getRoleCode().equals(user.getRole())) {
            return BaseResponse.fail("", "User not found or invalid role", null);
        }
        List<Appointment> appointments;
        if (search == null || search.isEmpty()) {
            appointments = appointmentMapper.getAppointmentsByProviderId(user.getUserId());
        } else {
            appointments =
                    appointmentMapper.getAppointmentsByProviderIdAndSearch(
                            user.getUserId(), search);
        }
        return BaseResponse.success(null, toAppointmentRecords(appointments));
    }

    private List<AppointmentRecord> toAppointmentRecords(List<Appointment> appointments) {
        List<AppointmentRecord> result = new ArrayList<>();
        if (appointments == null || appointments.isEmpty()) {
            return result;
        }
        for (Appointment appointment : appointments) {
            AppointmentRecord record = new AppointmentRecord();
            record.setDate(appointment.getDate());
            record.setStartTime(AppointTime.getAppointTime(appointment.getAppointTime()));
            BaseResponse<User> providerBaseResponse =
                    userService.getUserByUserId(appointment.getPatientId());
            if (ResponseCode.SUCCESS.getCode().equals(providerBaseResponse.getCode())) {
                User provider = providerBaseResponse.getData();
                record.setProviderName(provider.getFullName());
            }
            BaseResponse<User> patientBaseResponse =
                    userService.getUserByUserId(appointment.getPatientId());
            if (ResponseCode.SUCCESS.getCode().equals(patientBaseResponse.getCode())) {
                User patient = patientBaseResponse.getData();
                record.setPatientName(patient.getFullName());
            }
            AppointmentStatus status = AppointmentStatus.getStatusByCode(appointment.getStatus());
            record.setStatus(status != null ? status.getStatusName() : null);
            result.add(record);
        }
        return result;
    }
}
