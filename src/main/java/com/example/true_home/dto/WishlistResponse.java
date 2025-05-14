package com.example.true_home.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WishlistResponse {
    private boolean wishListFound;
    private List<WishlistOrOrderResponse> wishList;
}
