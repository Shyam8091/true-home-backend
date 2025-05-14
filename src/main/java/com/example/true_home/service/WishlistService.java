package com.example.true_home.service;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.Response;
import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.WishlistRequest;
import com.example.true_home.dto.WishlistResponse;
import com.example.true_home.entity.Listing;
import com.example.true_home.entity.Wishlist;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WishlistService {

    ResponseEntity<RestResponse<WishlistResponse>> getProductsByAccountId();

    ResponseEntity<RestResponse<Response>> addToWishList(CartRequestDto cartRequestDto);

    ResponseEntity<RestResponse<UpdateResponse>> deleteWishlistById(int wishlistId);

}
