package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthQA extends BaseEntity {

    private String healthId;

    private String userId;

    private String title;

    private String content;
}
