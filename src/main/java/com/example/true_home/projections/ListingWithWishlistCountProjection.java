package com.example.true_home.projections;

import com.example.true_home.entity.Listing;

public interface ListingWithWishlistCountProjection {
    Listing getListing();

    long getWishlistCount();

    boolean isWishlisted();
}
