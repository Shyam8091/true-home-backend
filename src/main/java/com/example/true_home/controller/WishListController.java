package com.example.true_home.controller;

import com.example.true_home.dto.CartRequestDto;
import com.example.true_home.dto.Response;
import com.example.true_home.dto.UpdateResponse;
import com.example.true_home.dto.WishlistResponse;
import com.example.true_home.service.WishlistService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
@Slf4j
public class WishListController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping(TrueHomeConstants.WISHLIST)
    public ResponseEntity<RestResponse<WishlistResponse>> getProductsByAccountId() {
        ResponseEntity<RestResponse<WishlistResponse>> wishList = wishlistService.getProductsByAccountId();
        return wishList;
    }

    @PostMapping(TrueHomeConstants.WISHLIST)
    public ResponseEntity<RestResponse<Response>> addToWishList(@RequestBody final CartRequestDto cartRequestDto) {
        ResponseEntity<RestResponse<Response>> wishListResponse = wishlistService.addToWishList(cartRequestDto);
        return wishListResponse;
    }

    @DeleteMapping("delete/{wishlistId}")
    public ResponseEntity<RestResponse<UpdateResponse>> deleteWishlistById(@PathVariable int wishlistId) {
        ResponseEntity<RestResponse<UpdateResponse>> restResponseResponseEntity = wishlistService.deleteWishlistById(wishlistId);
        return restResponseResponseEntity;
    }


}
