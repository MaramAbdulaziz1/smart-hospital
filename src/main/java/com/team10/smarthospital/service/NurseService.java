package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.NurseMapper;
import com.team10.smarthospital.model.entity.Nurse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NurseService implements IUserService<Nurse> {
    @Autowired private NurseMapper nurseMapper;

    @Override
    public void insertUser(Nurse user) {}
}
