package com.example.true_home.service.impl;

import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.VerifyOtpRequest;
import com.example.true_home.entity.OtpEntity;
import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.repository.OtpRepo;
import com.example.true_home.repository.UserRepo;
import com.example.true_home.service.OTPService;
import com.example.true_home.service.SendOtpService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import com.networknt.schema.JsonSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private TrueHomeUtil trueHomeUtil;

    @Autowired
    private JsonSchema jsonSchema;
    private final SendOtpService sendOtpService;

    OTPServiceImpl(@Qualifier("JavaEmailServiceImpl") SendOtpService sendOtpService) {
        this.sendOtpService = sendOtpService;
    }

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public ResponseEntity<RestResponse<UpdateResponse>> generateOtp(final UserRequestDto userRequestDto) {
        final User byMobile = userRepo.findByMobile(userRequestDto.getMobile());
        if (byMobile == null) {
            throw new TrueHomException(null, "Mobile not found", "OS_500");
        }
        final UpdateResponse updateResponse = new UpdateResponse();
        OtpEntity otpEntity = new OtpEntity();
        final String otp = String.format("%04d", secureRandom.nextInt(9999));
        System.out.println("otp " + otp);
//        sendOtpService.sendOtp(byMobile, otp);

        otpEntity.setOtpValue(otp);
        otpEntity.setUserId(byMobile.getId());
        otpRepo.save(otpEntity);
        HttpHeaders responseHeaders = trueHomeUtil.getHttpHeaders(byMobile.getId());
        updateResponse.setMessage("Email sent successfully! Please check you registered email");
        return RestUtils.successResponseWithHeaders(updateResponse, HttpStatus.OK, "Email sent successfully", responseHeaders);
    }


    @Override
    @Transactional
    public ResponseEntity<RestResponse<UpdateResponse>> verifyOtp(final VerifyOtpRequest verifyOtpRequest) {

        OtpEntity otpEntity = otpRepo.findByUserIdAndOtpValue(trueHomeUtil.getUserIdFromAuthentication(), verifyOtpRequest.getOtp());
        otpValidation(otpEntity);
        Timestamp currentTimeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Timestamp timeAfter5minutesOfOtpCreation = new Timestamp(otpEntity.getCreatedAt().getTime() + TimeUnit.MINUTES.toMillis(5));
        boolean before = currentTimeStamp.before(timeAfter5minutesOfOtpCreation);
        if (!before) {
            throw new TrueHomException(null, "OTP has expired, please re-generate your otp", "OS_500");
        }
        int i = otpRepo.updateOtpStatus(verifyOtpRequest.getOtp());
        if (i < 1) {
            throw new TrueHomException(null, "Something went wrong please try again", "OS_500");
        }
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setMessage("OTP verified successfully");
        return RestUtils.successResponse(updateResponse, HttpStatus.OK, "OTP verified successfully");
    }

    private void otpValidation(OtpEntity otpEntity) {
        if (otpEntity == null) {
            throw new TrueHomException("some data", "Invalid OTP", "OS_500");
        }
        if (!otpEntity.isActive()) {
            throw new TrueHomException(null, "OTP has expired, please re-generate your otp", "OS_500");
        }
    }
}
