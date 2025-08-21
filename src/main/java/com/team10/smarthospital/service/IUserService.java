package com.team10.smarthospital.service;

import com.team10.smarthospital.model.entity.User;

public interface IUserService<T extends User> {
    void insertUser(T user);
}
