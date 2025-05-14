package com.example.true_home.service;

import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.UserResponseDto;
import com.example.true_home.dto.UserSignUpResponseDto;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<RestResponse<UserSignUpResponseDto>> userSignup(UserRequestDto userRequestDto);

    ResponseEntity<RestResponse<UserResponseDto>> userDetails();
}
