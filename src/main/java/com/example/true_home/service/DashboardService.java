package com.example.true_home.service;

import com.example.true_home.dto.DashboardResponse;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

public interface DashboardService {

    ResponseEntity<RestResponse<DashboardResponse>> getDashboardData();


}
