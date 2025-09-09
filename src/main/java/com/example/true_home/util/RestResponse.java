package com.example.true_home.util;

import java.util.Map;

public class RestResponse<T> {
    private String message;
    private String errorCode;
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public RestResponse() {
    }

    public RestResponse(T data) {
        this(null, null, data);
    }

    public RestResponse(String errorCode, String message, T data) {
        this.message = message;
        this.errorCode = errorCode;
        this.data = data;
    }
}

