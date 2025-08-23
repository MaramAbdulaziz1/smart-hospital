package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.mapper.NurseMapper;
import com.team10.smarthospital.model.entity.Appointment;
import com.team10.smarthospital.model.entity.Doctor;
import com.team10.smarthospital.model.entity.Nurse;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.AppointTime;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.AppointmentRecord;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.DoctorDepartment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NurseService implements IUserService<Nurse> {

    @Autowired UserService userService;

    @Autowired private NurseMapper nurseMapper;

    @Autowired private AppointmentMapper appointmentMapper;

    @Override
    public void insertUser(Nurse user) {
        nurseMapper.insertUser(user);
    }

    public BaseResponse<List<DoctorDepartment>> getNurseByDepartment(Integer department) {
        List<DoctorDepartment> doctorDepartments = new ArrayList<>();
        List<Nurse> nurses = nurseMapper.getDoctorByDepartment(department);
        if (!nurses.isEmpty()) {
            for (Doctor doctor : nurses) {
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
            String nurseId, LocalDate date) {
        List<Appointment> appointments =
                appointmentMapper.getAppointmentsByProviderIdDate(nurseId, date);
        Set<Integer> bookedTimeCodes =
                appointments.stream().map(Appointment::getAppointTime).collect(Collectors.toSet());
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
}
