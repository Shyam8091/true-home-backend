package com.example.true_home.repository;

import com.example.true_home.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepo extends JpaRepository<OtpEntity, Integer> {

    @Query(value = "SELECT * FROM t_user_otp o WHERE o.created_at >= NOW() - INTERVAL 6 MINUTE AND o.user_id = :userId AND o.otp_value = :otp", nativeQuery = true)
    OtpEntity findByUserIdAndOtpValue(int userId, String otp);

    @Modifying
    @Query("update OtpEntity otpEntity set otpEntity.isActive = false where otpEntity.otpValue = :otpValue")
    int updateOtpStatus(@Param(value = "otpValue") String otpValue);
}
