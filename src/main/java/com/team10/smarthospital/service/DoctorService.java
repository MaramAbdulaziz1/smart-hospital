package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.DoctorMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.Doctor;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.AppointTime;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DoctorDepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService implements IUserService<Doctor> {

    @Autowired private DoctorMapper doctorMapper;

    @Autowired private UserService userService;

    @Autowired private AppointmentMapper appointmentMapper;

    @Override
    public void insertUser(Doctor user) {
        doctorMapper.insertUser(user);
    }

    public BaseResponse<List<DoctorDepartment>> getDoctorByDepartment(Integer department) {
        List<DoctorDepartment> doctorDepartments = new ArrayList<>();
        List<Doctor> doctors = doctorMapper.getDoctorByDepartment(department);
        if (!doctors.isEmpty()) {
            for (Doctor doctor : doctors) {
                BaseResponse<User> userBaseResponse =
                        userService.getUserByUserId(doctor.getUserId());
                if (ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
                    DoctorDepartment doctorDepartment = new DoctorDepartment();
                    doctorDepartment.setDoctorId(userBaseResponse.getData().getUserId());
                    doctorDepartment.setFullName(userBaseResponse.getData().getFullName());
                    doctorDepartments.add(doctorDepartment);
                }
            }
        }
        return BaseResponse.success(null, doctorDepartments);
    }

    public BaseResponse<List<AppointmentRecord>> getAppointmentTime(
            String email, String doctorId, LocalDate date) {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            String patientId = userBaseResponse.getData().getUserId();
            List<Appointment> appointments =
                    appointmentMapper.getAppointmentsByProviderIdPatientIdDate(
                            doctorId, patientId, date);
            Set<Integer> bookedTimeCodes =
                    appointments.stream()
                            .map(Appointment::getAppointTime)
                            .collect(Collectors.toSet());
            List<AppointmentRecord> availableTimes =
                    AppointTime.AVAILABLE_TIMES.values().stream()
                            .filter(time -> !bookedTimeCodes.contains(time.getCode()))
                            .map(
                                    time -> {
                                        AppointmentRecord record = new AppointmentRecord();
                                        record.setTimeCode(time.getCode());
                                        record.setStartTime(time.getStartTimeStr());
                                        return record;
                                    })
                            .sorted(Comparator.comparing(AppointmentRecord::getTimeCode))
                            .collect(Collectors.toList());
            return BaseResponse.success(null, availableTimes);
        }
        return BaseResponse.fail(userBaseResponse.getCode(), userBaseResponse.getMessage(), null);
    }
}
