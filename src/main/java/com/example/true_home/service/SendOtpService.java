package com.example.true_home.service;

import com.example.true_home.entity.User;

public interface SendOtpService {
    void sendOtp(User user, String otp);

}
