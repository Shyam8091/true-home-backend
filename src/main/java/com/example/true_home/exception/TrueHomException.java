package com.example.true_home.exception;

public class TrueHomException extends RuntimeException {
    private Object data;
    private String message;
    private String errorCode;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
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

    public TrueHomException(Object data, String message, String errorCode) {
        super(message);
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }
}
