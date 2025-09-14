package com.example.true_home.service;

import com.example.true_home.dto.AccountListingResponse;
import com.example.true_home.dto.ListingDto;
import com.example.true_home.dto.ListingResponseDto;
import com.example.true_home.entity.Listing;
import com.example.true_home.util.RestResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ListingService {
    ResponseEntity<RestResponse<Page<ListingResponseDto>>> getAllListings(final boolean isLoggedIn, final String type, final int page, final int size, final String city, List<String> locality, final String propertyType);

    ResponseEntity<RestResponse<Listing>> uploadListing(ListingDto listingDto);

    ResponseEntity<RestResponse<AccountListingResponse>> getListingFromAccount();

    ResponseEntity<RestResponse<ListingResponseDto>> getProductById(Integer id, final boolean isLoggedIn);

    ResponseEntity<RestResponse<List<ListingResponseDto>>> getHomePageListings(final boolean isLoggedIn);
}
