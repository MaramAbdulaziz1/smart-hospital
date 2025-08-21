package com.team10.smarthospital.controllers.api;

import com.team10.smarthospital.model.request.LoginRequest;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.UserResponse;
import com.team10.smarthospital.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserLoginController {

    @Autowired private LoginService loginService;

    @PostMapping("/login")
    public BaseResponse<UserResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        return loginService.login(loginRequest, request, response);
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return BaseResponse.success("Logout successful");
    }
}
