package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.User;

public interface BaseUserMapper<T extends User> {

    void insertUser(T user);

    T getUserByUserId(String userId);

    void updateUser(T user);
}
