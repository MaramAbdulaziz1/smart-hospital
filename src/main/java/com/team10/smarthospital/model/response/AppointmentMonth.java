package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentMonth {
    String appointmentId;
    LocalDate date;
    private String patientName;
}
