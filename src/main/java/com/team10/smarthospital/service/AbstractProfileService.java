package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.BaseUserMapper;
import com.team10.smarthospital.mapper.UserMapper;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.ProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractProfileService<
        T extends User, R extends ProfileResponse, M extends BaseUserMapper<T>> {

    @Autowired protected UserMapper userMapper;

    @Autowired protected M entityMapper;

    public BaseResponse<R> getUserProfile(String email) {
        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail("", "email can not be empty", null);
        }
        User user = userMapper.getUserByEmail(email);
        if (user == null) {
            return BaseResponse.fail("", "User not found", null);
        }
        T entity = entityMapper.getUserByUserId(user.getUserId());
        if (entity == null) {
            return BaseResponse.fail("", "Profile not found", null);
        }
        R response = buildProfileResponse(user, entity);
        return BaseResponse.success("", response);
    }

    protected abstract R buildProfileResponse(User user, T entity);
}
