package com.example.true_home.service.impl;

import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.UserResponseDto;
import com.example.true_home.dto.UserSignUpResponseDto;
import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.repository.UserRepo;
import com.example.true_home.security.CustomUserDetails;
import com.example.true_home.service.UserService;
import com.example.true_home.util.JWTUtility;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private TrueHomeUtil trueHomeUtil;

    @Override
    public ResponseEntity<RestResponse<UserSignUpResponseDto>> userSignup(UserRequestDto userRequestDto) {

        final UserSignUpResponseDto userSignUpResponseDto = new UserSignUpResponseDto();
        User user = userRepo.findByMobile(userRequestDto.getMobile());
        if (user != null) {
            String errorMessage = user.getIsActive() ? "User already exists please login" : "User Account is deactivated. Please contact the admin";
            throw new TrueHomException(null, errorMessage, "OS_500");
        }
        User userEntity = new User();
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setMobile(userRequestDto.getMobile());
        userEntity.setCity(userRequestDto.getCountry());
        userEntity.setFirstname(userRequestDto.getFirstname());
        userEntity.setLastname(userRequestDto.getLastname());
        userEntity.setCity(userRequestDto.getCity());
        userEntity.setState(userRequestDto.getState());
        userEntity.setPinCode(userRequestDto.getPinCode());
        User savedUser = userRepo.save(userEntity);
        userSignUpResponseDto.setEmail(savedUser.getEmail());
        HttpHeaders responseHeaders = trueHomeUtil.getHttpHeaders(savedUser.getId());
        return RestUtils.successResponseWithHeaders(userSignUpResponseDto, HttpStatus.OK, "Email sent successfully", responseHeaders);
    }

    @Override
    public ResponseEntity<RestResponse<UserResponseDto>> userDetails() {
        int id = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            id = userDetails.getUserId();
        }
        User user = userRepo.findById(id).orElseThrow(() -> new TrueHomException(null, "No user found", "OS_500"));
        UserResponseDto responseDto = UserResponseDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .city(user.getCity())
                .pinCode(user.getPinCode())
                .listedPropertyCount(1)
                .rentedPropertyCount(2)
                .purchasesPropertyCount(3)
                .build();

        return RestUtils.successResponse(responseDto, HttpStatus.OK, "User Details sent successfully");
    }
}
