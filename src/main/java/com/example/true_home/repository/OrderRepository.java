package com.example.true_home.repository;

import com.example.true_home.dto.WishlistOrOrderResponse;
import com.example.true_home.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


    @Query("SELECT new com.example.true_home.dto.WishlistOrOrderResponse(o.id , o.product) " +
            "FROM Order o WHERE o.account.id = :accountId")
    List<WishlistOrOrderResponse> findOrderByAccountId(int accountId);

}
