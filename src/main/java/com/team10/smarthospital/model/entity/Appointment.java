package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Appointment extends BaseEntity {

    private String appointmentId;

    private String patientId;

    private Integer type;

    private String providerId;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer status;

    private String cancellationReason;
}
