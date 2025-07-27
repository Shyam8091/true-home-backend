package com.example.true_home.service.impl;

import com.example.true_home.dto.DashboardResponse;
import com.example.true_home.repository.UserRepo;
import com.example.true_home.service.DashboardService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TrueHomeUtil trueHomeUtil;

    @Override
    public ResponseEntity<RestResponse<DashboardResponse>> getDashboardData() {

        DashboardResponse userDashboard = userRepo.getUserDashboard(trueHomeUtil.getUserIdFromAuthentication());

        return RestUtils.successResponse(userDashboard, HttpStatus.OK, "Dashboard fetched successfully");
    }
}
