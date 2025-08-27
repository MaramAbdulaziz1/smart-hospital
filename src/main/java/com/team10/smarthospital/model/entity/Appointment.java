package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Appointment extends BaseEntity {

    private String appointmentId;

    private String patientId;

    private String providerId;

    private LocalDate date;

    private Integer appointTime;

    private Integer status;

    private String cancellationReason;
}
