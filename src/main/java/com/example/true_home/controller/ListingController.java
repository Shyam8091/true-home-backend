package com.example.true_home.controller;

import com.example.true_home.dto.AccountListingResponse;
import com.example.true_home.dto.ListingDto;
import com.example.true_home.dto.ListingResponseDto;
import com.example.true_home.entity.Listing;
import com.example.true_home.service.ListingService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping(TrueHomeConstants.LISTING)
    public ResponseEntity<RestResponse<List<ListingResponseDto>>> getProductList(final @RequestHeader("isLogged") boolean isLoggedIn) {
        ResponseEntity<RestResponse<List<ListingResponseDto>>> products = listingService.getAllListings(isLoggedIn);
        return products;
    }

    @PostMapping(TrueHomeConstants.LISTING)
    public ResponseEntity<RestResponse<Listing>> uploadListing(@RequestBody ListingDto listingDto) {
        ResponseEntity<RestResponse<Listing>> restResponseResponseEntity = listingService.uploadListing(listingDto);
        return restResponseResponseEntity;
    }

    @GetMapping(TrueHomeConstants.ACCOUNT_LISTING)
    public ResponseEntity<RestResponse<AccountListingResponse>> getListingFromAccount() {
        ResponseEntity<RestResponse<AccountListingResponse>> restResponseResponseEntity = listingService.getListingFromAccount();
        return restResponseResponseEntity;
    }

    @GetMapping(TrueHomeConstants.LISTING_BY_ID)
    public ResponseEntity<RestResponse<ListingResponseDto>> getProductById(final @PathVariable Integer id) {
        ResponseEntity<RestResponse<ListingResponseDto>> products = listingService.getProductById(id);
        return products;
    }
}
