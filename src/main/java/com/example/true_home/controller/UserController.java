package com.example.true_home.controller;

import com.example.true_home.dto.UserRequestDto;
import com.example.true_home.dto.UserResponseDto;
import com.example.true_home.dto.UserSignUpResponseDto;
import com.example.true_home.service.UserService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(TrueHomeConstants.USER_SIGNUP)
    public ResponseEntity<RestResponse<UserSignUpResponseDto>> userSignup(@RequestBody final UserRequestDto userRequestDto) {
        ResponseEntity<RestResponse<UserSignUpResponseDto>> restResponseResponseEntity = userService.userSignup(userRequestDto);
        return restResponseResponseEntity;
    }

    @GetMapping(TrueHomeConstants.USER)
    public ResponseEntity<RestResponse<UserResponseDto>> userDetails() {
        ResponseEntity<RestResponse<UserResponseDto>> restResponseResponseEntity = userService.userDetails();
        return restResponseResponseEntity;
    }

}
