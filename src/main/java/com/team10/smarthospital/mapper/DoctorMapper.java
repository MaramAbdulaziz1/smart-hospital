package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.Doctor;

import java.util.List;

public interface DoctorMapper extends BaseUserMapper<Doctor> {
    List<Doctor> getDoctorByDepartment(Integer department);
}
