package com.team10.smarthospital.service;

import com.team10.smarthospital.mapper.BaseUserMapper;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.ProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractProfileService<
        T extends User, R extends ProfileResponse, M extends BaseUserMapper<T>> {

    @Autowired protected M entityMapper;

    @Autowired private UserService userService;

    public BaseResponse<R> getUserProfile(String email) {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (!ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            return BaseResponse.fail(
                    userBaseResponse.getCode(), userBaseResponse.getMessage(), null);
        }
        User user = userBaseResponse.getData();
        T entity = entityMapper.getUserByUserId(user.getUserId());
        if (entity == null) {
            return BaseResponse.fail("", "Profile not found", null);
        }
        R response = buildProfileResponse(user, entity);
        return BaseResponse.success("", response);
    }

    protected abstract R buildProfileResponse(User user, T entity);
}
