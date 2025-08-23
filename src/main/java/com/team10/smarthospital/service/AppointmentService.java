package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.AppointTime;
import com.team10.smarthospital.model.enums.AppointmentStatus;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.enums.Role;
import com.team10.smarthospital.model.request.AppointmentBook;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class AppointmentService {

    @Autowired private UserService userService;

    @Autowired private AppointmentMapper appointmentMapper;

    public BaseResponse<List<AppointmentRecord>> getPatientUpcoming(String email) {
        BaseResponse<User> userResp = getUserByEmailAndRole(email, Role.PATIENT.getRoleCode());
        if (!ResponseCode.SUCCESS.getCode().equals(userResp.getCode())) {
            return BaseResponse.fail(userResp.getCode(), userResp.getMessage(), null);
        }
        List<Appointment> appointments =
                appointmentMapper.getAppointmentsUpcomingByPatientId(
                        userResp.getData().getUserId());
        List<AppointmentRecord> result = toAppointmentRecords(appointments);
        return BaseResponse.success("", result);
    }

    public BaseResponse<List<AppointmentRecord>> getAppointmentPatient(String email) {
        return getAppointmentsByRole(
                email, Role.PATIENT.getRoleCode(), appointmentMapper::getAppointmentsByPatientId);
    }

    public BaseResponse<List<AppointmentRecord>> getAppointmentDoctor(String email) {
        return getAppointmentsByRole(
                email, Role.DOCTOR.getRoleCode(), appointmentMapper::getAppointmentsByProviderId);
    }

    public BaseResponse<List<AppointmentRecord>> getAppointmentNurse(String email) {
        return getAppointmentsByRole(
                email, Role.NURSE.getRoleCode(), appointmentMapper::getAppointmentsByProviderId);
    }

    public BaseResponse<String> book(String email, AppointmentBook appointmentBook) {
        BaseResponse<User> userResp = getUserByEmailAndRole(email, Role.PATIENT.getRoleCode());
        if (!ResponseCode.SUCCESS.getCode().equals(userResp.getCode())) {
            return BaseResponse.fail(userResp.getCode(), userResp.getMessage(), null);
        }
        final Appointment appointment = toAppointment(appointmentBook);
        try {
            appointment.setPatientId(userResp.getData().getUserId());
            appointmentMapper.insertAppointment(appointment);
            return BaseResponse.success("successful", null);
        } catch (Exception e) {
            return BaseResponse.fail("", e.getMessage(), null);
        }
    }

    private Appointment toAppointment(AppointmentBook appointmentBook) {
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentBook.getDate());
        appointment.setAppointTime(appointmentBook.getTime());
        appointment.setProviderId(appointmentBook.getProviderId());
        appointment.setType(appointmentBook.getType());
        appointment.setAppointTime(appointmentBook.getTime());
        appointment.setStatus(AppointmentStatus.UPCOMING.getStatusCode());
        return appointment;
    }

    private BaseResponse<User> getUserByEmailAndRole(String email, Integer roleCode) {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (!ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            return BaseResponse.fail(
                    userBaseResponse.getCode(), userBaseResponse.getMessage(), null);
        }
        User user = userBaseResponse.getData();
        if (!roleCode.equals(user.getRole())) {
            return BaseResponse.fail(
                    ResponseCode.USER_NOT_FOUND.getCode(), "User not found or invalid role", null);
        }
        return BaseResponse.success(null, user);
    }

    private List<AppointmentRecord> toAppointmentRecords(List<Appointment> appointments) {
        List<AppointmentRecord> result = new ArrayList<>();
        if (appointments == null || appointments.isEmpty()) {
            return result;
        }
        for (Appointment appointment : appointments) {
            AppointmentRecord record = new AppointmentRecord();
            record.setAppointmentId(appointment.getAppointmentId());
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

    private BaseResponse<List<AppointmentRecord>> getAppointmentsByRole(
            String email, Integer roleCode, Function<String, List<Appointment>> fetcher) {
        BaseResponse<User> userResp = getUserByEmailAndRole(email, roleCode);
        if (!ResponseCode.SUCCESS.getCode().equals(userResp.getCode())) {
            return BaseResponse.fail(userResp.getCode(), userResp.getMessage(), null);
        }
        List<Appointment> appointments = fetcher.apply(userResp.getData().getUserId());
        List<AppointmentRecord> result = toAppointmentRecords(appointments);
        return BaseResponse.success("", result);
    }

    public BaseResponse<Void> cancelAppointment(String email, String appointmentId) {
        BaseResponse<User> userResp = userService.getUserByEmail(email);
        if (!ResponseCode.SUCCESS.getCode().equals(userResp.getCode())) {
            return BaseResponse.fail(userResp.getCode(), userResp.getMessage(), null);
        }
        appointmentMapper.updateStatusByPatientId(
                userResp.getData().getUserId(),
                appointmentId,
                AppointmentStatus.CANCELLED.getStatusCode());
        return BaseResponse.success(null);
    }
}
