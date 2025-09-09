package com.example.true_home.dto;

import com.example.true_home.entity.Listing;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class WishlistOrOrderOrAccountListingResponse {

    private int id;

    private Listing product;

    public WishlistOrOrderOrAccountListingResponse(int id, Listing product) {
        this.id = id;
        this.product = product;
    }

}
