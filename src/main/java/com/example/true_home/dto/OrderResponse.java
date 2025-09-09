package com.example.true_home.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {
    private boolean orderFound;
    private int rentedPropertyCount;
    private int purchasesPropertyCount;
    private List<WishlistOrOrderOrAccountListingResponse> orders;
}
