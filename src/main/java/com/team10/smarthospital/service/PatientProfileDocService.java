package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.PatientMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.Patient;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.PatientProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientProfileDocService extends PatientProfileService {

    @Autowired AppointmentMapper appointmentMapper;

    @Autowired PatientMapper patientMapper;

    @Autowired private UserService userService;

    @Override
    public BaseResponse<PatientProfileResponse> getUserProfile(String appointmentId) {
        Appointment appointment = appointmentMapper.getAppointmentsById(appointmentId);
        if (appointment == null) {
            return BaseResponse.fail("", "appointmentId error", null);
        }
        BaseResponse<User> userBaseResponse =
                userService.getUserByUserId(appointment.getPatientId());
        if (!ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            return BaseResponse.fail(
                    userBaseResponse.getCode(), userBaseResponse.getMessage(), null);
        }
        User user = userBaseResponse.getData();
        Patient entity = patientMapper.getUserByUserId(user.getUserId());
        if (entity == null) {
            return BaseResponse.fail("", "Profile not found", null);
        }
        PatientProfileResponse response = buildProfileResponse(user, entity);
        return BaseResponse.success("", response);
    }
}
