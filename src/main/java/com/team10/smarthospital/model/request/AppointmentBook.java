package com.team10.smarthospital.model.request;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentBook {
    private LocalDate date;
    private Integer time;
    private Integer type;
    private String providerId;
}
