package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.DoctorMapper;
import com.team10.smarthospital.model.entity.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService implements IUserService<Doctor> {
    @Autowired private DoctorMapper doctorMapper;

    @Override
    public void insertUser(Doctor user) {}
}
