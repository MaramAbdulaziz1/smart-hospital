package com.team10.smarthospital.model.conf;

import com.team10.smarthospital.model.response.BaseResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        BaseResponse<Object> response = new BaseResponse<>();
        response.setCode("-1");
        response.setMessage("Operation failed: " + e.getMessage());
        return response;
    }
}
