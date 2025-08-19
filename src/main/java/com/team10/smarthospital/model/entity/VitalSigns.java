package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VitalSigns extends BaseEntity {
    private Integer recordId;
    private String patientId;
    private String nurseId;
    private String bloodPressure;
    private Integer heartRate;
    private BigDecimal bodyTemperature;
    private BigDecimal weight;
    private BigDecimal height;
}
