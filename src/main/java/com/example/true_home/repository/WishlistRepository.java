package com.example.true_home.repository;

import com.example.true_home.dto.WishlistOrOrderOrAccountListingResponse;
import com.example.true_home.entity.Listing;
import com.example.true_home.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByAccount_Id(int accountId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO true_home.T_WISHLIST (accountId, productId) VALUES (:accountId, :productId)", nativeQuery = true)
    Listing addToWishList(int accountId, int productId);

    @Query("SELECT w.product FROM Wishlist w WHERE w.account.id = :accountId")
    List<Listing> findProductsByAccountId(int accountId);

    @Query("SELECT new com.example.true_home.dto.WishlistOrOrderOrAccountListingResponse(w.id , w.product) " +
            "FROM Wishlist w WHERE w.account.id = :accountId")
    List<WishlistOrOrderOrAccountListingResponse> findOrderAndProductByAccountId(int accountId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Wishlist w WHERE w.id = :wishlistId AND w.account.id = :accountId")
    int deleteWishlistById(int wishlistId, int accountId);

}
