package com.example.true_home.repository;

import com.example.true_home.entity.Listing;
import com.example.true_home.projections.ListingWithWishlistCountProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Integer> {
    @Query(value = "SELECT * FROM t_listing LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Listing> findAllListings(int limit, int offset);

    @Query(value = "SELECT * FROM t_listing l WHERE l.ownerId = :accountId", nativeQuery = true)
    List<Listing> getListingFromAccount(int accountId);

    @Query("SELECT l AS listing, COUNT(w.id) AS wishlistCount, false AS wishlisted " +
            "FROM Listing l " +
            "LEFT JOIN Wishlist w ON w.product = l " +
            "GROUP BY l.id")
    List<ListingWithWishlistCountProjection> findAllListingsWithWishlistCount();


    @Query("SELECT l AS listing, " +
            "COUNT(w.id) AS wishlistCount, " +
            "(w.id) AS wishlistedId, " +
            "CASE WHEN COUNT(w2.id) > 0 THEN true ELSE false END AS wishlisted " +
            "FROM Listing l " +
            "LEFT JOIN Wishlist w ON w.product = l " +
            "LEFT JOIN Wishlist w2 ON w2.product = l AND w2.account.id = :accountId " +
            "GROUP BY l.id")
    List<ListingWithWishlistCountProjection> findAllListingsWithWishlistCount(@Param("accountId") Integer accountId);

    @Query("SELECT l AS listing , " +
            "COUNT(w.id) AS wishlistCount , " +
            "CASE WHEN COUNT(w2.id) > 0 THEN true ELSE false END AS wishlisted , " +
            "MAX(w2.id) AS wishlistedId " +
            " FROM Listing l " +
            " LEFT JOIN Wishlist w ON w.product = l " +
            " LEFT JOIN Wishlist w2 ON w2.product = l AND w2.account.id = :accountId " +
            " WHERE l.id = :listingId " +
            " GROUP BY l ")
    ListingWithWishlistCountProjection findListingWithWishlistInfo(
            @Param("listingId") Integer listingId,
            @Param("accountId") Integer accountId);

    @Query("SELECT l AS listing , " +
            "COUNT(w.id) AS wishlistCount, " +
            "false AS wishlisted "+
            "FROM Listing l " +
            "LEFT JOIN Wishlist w ON w.product = l " +
            "WHERE l.id = :listingId " +
            "GROUP BY l")
    ListingWithWishlistCountProjection findListingWithWishlistInfo(@Param("listingId") Integer listingId);


}
