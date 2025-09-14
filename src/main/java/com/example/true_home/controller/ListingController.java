package com.example.true_home.controller;

import com.example.true_home.dto.AccountListingResponse;
import com.example.true_home.dto.ListingDto;
import com.example.true_home.dto.ListingResponseDto;
import com.example.true_home.entity.Listing;
import com.example.true_home.service.ListingService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)

@Validated

public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping(TrueHomeConstants.LISTING)

    public ResponseEntity<RestResponse<Page<ListingResponseDto>>> getProductList(final @RequestHeader("isLogged") boolean isLoggedIn,
                                                                                 final @RequestParam(name = "type", required = false)
                                                                                 @Pattern(regexp = "Rent|Sell|", message = "Type Does not match")
                                                                                 String type,
                                                                                 final @RequestParam(defaultValue = "0")
                                                                                 @Min(value = 0, message = "Invalid Page")
                                                                                 @Max(value = 1000, message = "Invalid Page")
                                                                                 int page,
                                                                                 final @RequestParam(defaultValue = "10")
                                                                                 @Min(value = 1, message = "Invalid Size")
                                                                                 @Max(value = 100, message = "Invalid Size")
                                                                                 int size,
                                                                                 @Size(max = 28, message = "Invalid city") final @RequestParam(name = "city", required = false) String city, @RequestParam(name = "locality", required = false) List<String> locality, @RequestParam(name = "propertyType", required = false) String propertyType) {

        ResponseEntity<RestResponse<Page<ListingResponseDto>>> products = listingService.getAllListings(isLoggedIn, type, page, size, city, locality, propertyType);


        return products;
    }

    @PostMapping(TrueHomeConstants.LISTING)

    public ResponseEntity<RestResponse<Listing>> uploadListing(@ModelAttribute ListingDto formData) {
        ResponseEntity<RestResponse<Listing>> restResponseResponseEntity = listingService.uploadListing(formData);

        return restResponseResponseEntity;
    }

    @GetMapping(TrueHomeConstants.ACCOUNT_LISTING)
    public ResponseEntity<RestResponse<AccountListingResponse>> getListingFromAccount() {
        ResponseEntity<RestResponse<AccountListingResponse>> restResponseResponseEntity = listingService.getListingFromAccount();
        return restResponseResponseEntity;
    }

    @GetMapping(TrueHomeConstants.LISTING_BY_ID)
    public ResponseEntity<RestResponse<ListingResponseDto>> getProductById(final @PathVariable Integer id,
                                                                           final @RequestHeader("isLogged") boolean isLoggedIn) {
        ResponseEntity<RestResponse<ListingResponseDto>> products = listingService.getProductById(id, isLoggedIn);
        return products;
    }


    @GetMapping(TrueHomeConstants.HOMEPAGE_LISTING)
    public ResponseEntity<RestResponse<List<ListingResponseDto>>> getHomePageListings(
            final @RequestHeader("isLogged") boolean isLoggedIn) {
        ResponseEntity<RestResponse<List<ListingResponseDto>>> products = listingService.getHomePageListings(isLoggedIn);
        return products;
    }

}
