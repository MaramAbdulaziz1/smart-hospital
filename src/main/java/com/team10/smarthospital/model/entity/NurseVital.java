package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NurseVital extends BaseEntity {

    private String vitalId;

    private String appointmentId;

    private Integer heartRate;

    private String bloodPressure;

    private BigDecimal temperature;

    private BigDecimal weight;

    private BigDecimal height;

    private BigDecimal bmi;

    private BigDecimal glucose;

    private BigDecimal o2Saturation;

    private BigDecimal respiratoryRate;

    private String nurseName;
}
