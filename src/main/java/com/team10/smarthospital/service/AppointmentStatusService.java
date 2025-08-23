package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.AppointmentMapper;
import com.team10.smarthospital.model.enums.AppointmentStatus;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AppointmentStatusService {

    @Autowired private AppointmentMapper appointmentMapper;

    @PostConstruct
    public void expiryAppointment() {
        appointmentMapper.updateStatusByDate(
                LocalDate.now(), AppointmentStatus.EXPIRED.getStatusCode());
    }
}
