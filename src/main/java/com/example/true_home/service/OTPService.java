package com.example.true_home.service;

import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.VerifyOtpRequest;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

public interface OTPService {
    ResponseEntity<RestResponse<UpdateResponse>> generateOtp(final UserRequestDto userRequestDto);

    ResponseEntity<RestResponse<UpdateResponse>> verifyOtp(final VerifyOtpRequest verifyOtpRequest);
}
