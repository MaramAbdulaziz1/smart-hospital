package com.team10.smarthospital.service;

import com.team10.smarthospital.model.conf.CustomUserDetails;
import com.team10.smarthospital.model.request.LoginRequest;
import com.team10.smarthospital.model.response.BaseResponse;
import com.team10.smarthospital.model.response.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired private AuthenticationManager authenticationManager;

    public BaseResponse<UserResponse> login(LoginRequest request) {
        String email = request.getUsername();
        String password = request.getPassword();

        if (email == null || email.trim().isEmpty()) {
            return BaseResponse.fail("403004", "Email cannot be empty", null);
        }
        if (password == null || password.isEmpty()) {
            return BaseResponse.fail("403004", "Password cannot be empty", null);
        }

        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserResponse userResponse = new UserResponse();
            userResponse.setUserId(userDetails.getUserId());
            userResponse.setRole(userDetails.getRole());
            userResponse.setEmail(userDetails.getUsername());

            return BaseResponse.success("Login success", userResponse);

        } catch (BadCredentialsException e) {
            return BaseResponse.fail("403001", "Email or password error", null);
        } catch (DisabledException e) {
            return BaseResponse.fail("403003", "Account disabled", null);
        } catch (LockedException e) {
            return BaseResponse.fail("403004", "Account locked", null);
        } catch (AccountExpiredException e) {
            return BaseResponse.fail("403005", "Account expired", null);
        } catch (CredentialsExpiredException e) {
            return BaseResponse.fail("403006", "Credentials expired", null);
        } catch (Exception e) {
            return BaseResponse.fail("500", "Login failed due to server error", null);
        }
    }
}
