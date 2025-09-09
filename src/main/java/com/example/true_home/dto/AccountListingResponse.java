package com.example.true_home.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AccountListingResponse {
    private boolean listingsFound;
    private List<WishlistOrOrderOrAccountListingResponse> accountListings;

}
