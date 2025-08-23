package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Nurse;

import java.util.List;

public interface NurseMapper extends BaseUserMapper<Nurse> {
    List<Nurse> getDoctorByDepartment(Integer department);
}
