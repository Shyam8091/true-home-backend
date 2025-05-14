package com.example.true_home.controller;

import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.VerifyOtpRequest;
import com.example.true_home.service.OTPService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class OTPController {
    @Autowired
    private OTPService otpService;


    @PostMapping(TrueHomeConstants.GENERATE_OTP)
    public ResponseEntity<RestResponse<UpdateResponse>> generateOtp(@RequestBody final UserRequestDto userRequestDto) {
        ResponseEntity<RestResponse<UpdateResponse>> restResponseResponseEntity = otpService.generateOtp(userRequestDto);
        return restResponseResponseEntity;
    }

    @PostMapping(TrueHomeConstants.VERIFY_OTP)
    public ResponseEntity<RestResponse<UpdateResponse>> verifyOtp(@RequestBody final VerifyOtpRequest verifyOtpRequest) throws Exception {
        ResponseEntity<RestResponse<UpdateResponse>> restResponseResponseEntity = otpService.verifyOtp(verifyOtpRequest);
        return restResponseResponseEntity;
    }
}
