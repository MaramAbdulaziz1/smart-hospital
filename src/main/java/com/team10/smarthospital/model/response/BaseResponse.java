package com.team10.smarthospital.model.response;

import com.team10.smarthospital.model.enums.ResponseCode;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseResponse<T> {
    private String code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success(String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> fail(String code, String message, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> BaseResponse<T> fail(ResponseCode responseCode, T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(responseCode.getCode());
        response.setMessage(responseCode.getMessage());
        response.setData(data);
        return response;
    }

    public static BaseResponse<Void> success(String message) {
        BaseResponse<Void> response = new BaseResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(message);
        return response;
    }

    public static BaseResponse<Void> fail(String code, String message) {
        BaseResponse<Void> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
