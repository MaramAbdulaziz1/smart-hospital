package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Medication extends BaseEntity {

    private String medicationId;

    private String prescriptionId;

    private String name;

    private String dosage;

    private String frequency;

    private String note;

    private String route;

    private String duration;
}
