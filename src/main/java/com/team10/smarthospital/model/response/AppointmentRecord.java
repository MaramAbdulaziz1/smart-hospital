package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentRecord {
    private String appointmentId;
    private LocalDate date;
    private String providerName;
    private String patientName;
    private String patientCode;
    private String patientEmail;
    private String status;
    private Integer timeCode;
    private String startTime;
}
