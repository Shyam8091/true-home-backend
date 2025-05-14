package com.example.true_home.service;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.OrderResponse;
import com.example.true_home.dto.Response;
import com.example.true_home.entity.Order;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    ResponseEntity<RestResponse<OrderResponse>> getAllOrdersByAccountId();

    ResponseEntity<RestResponse<Response>> placeOrder(CartRequestDto cartRequestDto);
}
