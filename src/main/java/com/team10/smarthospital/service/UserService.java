package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService<User> {

    @Autowired private UserMapper userMapper;

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void insertUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    public BaseResponse<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail(ResponseCode.PARAMETER_EMPTY, null);
        }
        User user = userMapper.getUserByEmail(email);
        if (user == null) {
            return BaseResponse.fail(ResponseCode.USER_NOT_FOUND, null);
        }
        return BaseResponse.success(null, user);
    }

    public BaseResponse<User> getUserByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return BaseResponse.fail(ResponseCode.PARAMETER_EMPTY, null);
        }
        User user = userMapper.getUserByUserId(userId);
        if (user == null) {
            return BaseResponse.fail(ResponseCode.USER_NOT_FOUND, null);
        }
        return BaseResponse.success(null, user);
    }

    public void resetPassword(String userId, String password) {
        userMapper.resetPassword(userId, passwordEncoder.encode(password));
    }
}
