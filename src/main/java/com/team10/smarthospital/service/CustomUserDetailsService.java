package com.team10.smarthospital.service;

import com.team10.smarthospital.model.conf.CustomUserDetails;
import com.team10.smarthospital.model.entity.User;
import com.team10.smarthospital.model.enums.ResponseCode;
import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseResponse<User> userBaseResponse = userService.getUserByEmail(email);
        if (ResponseCode.SUCCESS.getCode().equals(userBaseResponse.getCode())) {
            com.team10.smarthospital.model.entity.User user = userBaseResponse.getData();
            return new CustomUserDetails(
                    user.getUserId(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole(),
                    true,
                    true,
                    true,
                    true);
        }
        if (ResponseCode.USER_NOT_FOUND.getCode().equals(userBaseResponse.getCode())) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        } else {
            throw new RuntimeException(userBaseResponse.getMessage());
        }
    }
}
