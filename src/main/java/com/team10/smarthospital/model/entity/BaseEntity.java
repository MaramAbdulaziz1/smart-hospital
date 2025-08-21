package com.team10.smarthospital.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseEntity {

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
