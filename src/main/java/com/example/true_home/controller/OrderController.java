package com.example.true_home.controller;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.OrderResponse;
import com.example.true_home.dto.Response;
import com.example.true_home.entity.Order;
import com.example.true_home.service.OrderService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(TrueHomeConstants.ORDERS)
    public ResponseEntity<RestResponse<OrderResponse>> getAllOrdersByAccountId() {
        ResponseEntity<RestResponse<OrderResponse>> wishListResponse = orderService.getAllOrdersByAccountId();
        return wishListResponse;
    }

    @PostMapping(TrueHomeConstants.ORDERS)
    public ResponseEntity<RestResponse<Response>> placeOrder(@RequestBody final CartRequestDto cartRequestDto) {
        ResponseEntity<RestResponse<Response>> wishListResponse = orderService.placeOrder(cartRequestDto);
        return wishListResponse;
    }

}
