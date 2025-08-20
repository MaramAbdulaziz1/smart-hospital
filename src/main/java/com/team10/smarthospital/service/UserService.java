package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.User;

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

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
