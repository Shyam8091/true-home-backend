package com.example.true_home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DashboardResponse {

    private String firstName;
    private String lastName;
    private String email;

    private Long wishlistCount;
    private Long listedCount;

    private Long buySellCount;
    private Long buyRentCount;
}
