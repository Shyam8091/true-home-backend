package com.example.true_home.service.impl;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.OrderResponse;
import com.example.true_home.dto.Response;
import com.example.true_home.dto.WishlistOrOrderOrAccountListingResponse;
import com.example.true_home.entity.Listing;
import com.example.true_home.entity.Order;
import com.example.true_home.entity.User;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.repository.OrderRepository;
import com.example.true_home.service.OrderService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TrueHomeUtil trueHomeUtil;

    @Override
    public ResponseEntity<RestResponse<OrderResponse>> getAllOrdersByAccountId() {
        List<WishlistOrOrderOrAccountListingResponse> orderList = orderRepository.findOrderByAccountId(trueHomeUtil.getUserIdFromAuthentication());
        Map<String, Integer> collect = orderList.stream().collect(Collectors.groupingBy(order -> order.getProduct().getType(), Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        OrderResponse response = OrderResponse.builder()
                .orders(orderList)
                .orderFound(!orderList.isEmpty())
                .rentedPropertyCount(collect.getOrDefault("Rent", 0))
                .purchasesPropertyCount(collect.getOrDefault("Sell", 0))
                .build();
        return RestUtils.successResponse(response, HttpStatus.OK, "Fetched All Orders successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<RestResponse<Response>> placeOrder(CartRequestDto cartRequestDto) {
        Order order = new Order();
        order.setAccount(new User(trueHomeUtil.getUserIdFromAuthentication()));
        order.setProduct(new Listing(cartRequestDto.getListingId()));
        try {
            orderRepository.save(order);
        } catch (Exception exception) {
            throw new TrueHomException(null, "INTERNAL_SERVER_ERROR", "OS_500");
        }
        return RestUtils.successResponse(Response.builder().message("Order Placed Successfully").build(), HttpStatus.OK, "Fetched All wishlist successfully");

    }
}
