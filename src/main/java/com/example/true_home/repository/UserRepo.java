package com.example.true_home.repository;

import com.example.true_home.dto.DashboardResponse;
import com.example.true_home.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByMobile(String email);

    @Query(value = "SELECT new com.example.true_home.dto.DashboardResponse(u.firstname, u.lastname, u.email, " +
            "(SELECT COUNT(w.id) FROM Wishlist w WHERE w.account.id = u.id), " +
            "(SELECT COUNT(l.id) FROM Listing l WHERE l.owner.id = u.id)," +
            "(SELECT COUNT(o1.id) FROM Order o1 JOIN o1.product p1 WHERE o1.account.id = u.id AND p1.type = 'Sell')," +
            "(SELECT COUNT(o2.id) FROM Order o2 JOIN o2.product p2 WHERE o2.account.id = u.id AND p2.type = 'Rent'))" +
            "FROM User u WHERE u.id = :userId")
    DashboardResponse getUserDashboard(@Param("userId") Integer userId);

}
