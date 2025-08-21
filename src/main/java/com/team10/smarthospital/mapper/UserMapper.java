package com.team10.smarthospital.mapper;

import com.team10.smarthospital.model.entity.User;

public interface UserMapper extends BaseUserMapper<User> {
    User getUserByEmail(String email);
}
