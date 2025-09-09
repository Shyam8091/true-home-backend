package com.example.true_home.controller;

import com.example.true_home.dto.DashboardResponse;
import com.example.true_home.service.DashboardService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping(TrueHomeConstants.DASHBOARD)
    public ResponseEntity<RestResponse<DashboardResponse>> getListingFromAccount() {
        ResponseEntity<RestResponse<DashboardResponse>> restResponseResponseEntity = dashboardService.getDashboardData();
        return restResponseResponseEntity;
    }
}
