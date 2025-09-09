package com.example.true_home.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private String email;
    private String mobile;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private String state;
    private String pinCode;
    private int rentedPropertyCount;
    private int purchasesPropertyCount;
    private int listedPropertyCount;


}
