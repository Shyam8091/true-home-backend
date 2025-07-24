package com.example.true_home.service;

import com.example.true_home.dto.AccountListingResponse;
import com.example.true_home.dto.ListingDto;
import com.example.true_home.dto.ListingResponseDto;
import com.example.true_home.entity.Listing;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ListingService {
    ResponseEntity<RestResponse<List<ListingResponseDto>>> getAllListings(final boolean isLoggedIn);

    ResponseEntity<RestResponse<Listing>> uploadListing(ListingDto listingDto);

    ResponseEntity<RestResponse<AccountListingResponse>> getListingFromAccount();

    ResponseEntity<RestResponse<ListingResponseDto>> getProductById(Integer id, final boolean isLoggedIn);
}
