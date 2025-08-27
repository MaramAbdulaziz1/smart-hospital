package com.team10.smarthospital.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class VitalsRecord {
    private LocalDate date;
    private String bp;
    private Integer heartRate;
    private BigDecimal temperature;
    private BigDecimal weight;
    private BigDecimal height;
    private BigDecimal bmi;
    private BigDecimal glucose;
    private BigDecimal o2Saturation;
    private BigDecimal respiratoryRate;
    private String nurseName;
}
