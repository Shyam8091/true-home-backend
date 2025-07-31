package com.example.true_home.service.impl;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.Response;
import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.WishlistOrOrderOrAccountListingResponse;
import com.example.true_home.dto.WishlistResponse;
import com.example.true_home.entity.Listing;
import com.example.true_home.entity.User;
import com.example.true_home.entity.Wishlist;
import com.example.true_home.exception.TrueHomException;
import com.example.true_home.repository.WishlistRepository;
import com.example.true_home.service.WishlistService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import com.example.true_home.util.TrueHomeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WishListServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private TrueHomeUtil trueHomeUtil;


    @Override
    public ResponseEntity<RestResponse<WishlistResponse>> getProductsByAccountId() {
        List<WishlistOrOrderOrAccountListingResponse> productsByAccountId = wishlistRepository.findOrderAndProductByAccountId(trueHomeUtil.getUserIdFromAuthentication());
        return RestUtils.successResponse(WishlistResponse.builder().wishList(productsByAccountId).wishListFound(!productsByAccountId.isEmpty()).build(), HttpStatus.OK, "Fetched All wishlist successfully");
    }

    @Override
    @Transactional
    public ResponseEntity<RestResponse<Response>> addToWishList(CartRequestDto cartRequestDto) {
        Wishlist wishlist = new Wishlist();
        long id;
        wishlist.setAccount(new User(trueHomeUtil.getUserIdFromAuthentication())); // Assuming a constructor or setter exists in User class
        wishlist.setProduct(new Listing(cartRequestDto.getListingId())); // Assuming a constructor or setter exists in Listing class
        try {
            Wishlist save = wishlistRepository.save(wishlist);
            id = save.getId();
        } catch (Exception exception) {
            throw new TrueHomException(null, "INTERNAL_SERVER_ERROR", "OS_500");
        }
        return RestUtils.successResponse(Response.builder().message("Added to WishList successfully").id(id).build(), HttpStatus.OK, "Fetched All wishlist successfully");

    }

    public ResponseEntity<RestResponse<UpdateResponse>> deleteWishlistById(int wishlistId) {
        final UpdateResponse updateResponse = new UpdateResponse();
        int i = wishlistRepository.deleteWishlistById(wishlistId, trueHomeUtil.getUserIdFromAuthentication());
        if (i < 1) {
            throw new TrueHomException(null, "Something went wrong please try again", "OS_500");
        }
        updateResponse.setMessage("Wishlist removed successfully");
        return RestUtils.successResponse(updateResponse, HttpStatus.OK, "Wishlist updated successfully");
    }

}
