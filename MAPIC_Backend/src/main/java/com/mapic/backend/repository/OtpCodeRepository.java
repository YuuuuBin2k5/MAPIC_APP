package com.mapic.backend.repository;

import com.mapic.backend.entity.OtpCode;
import com.mapic.backend.entity.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    // Tìm OTP còn hạn sử dụng và chưa bị xài
    // ?1 là tham số thứ nhất (email), ?2 là tham số thứ 2 (otpCode), ?3 là type
    @Query("SELECT o FROM OtpCode o WHERE o.email = ?1 AND o.otpCode = ?2 AND o.type = ?3 AND o.isUsed = false AND o.expiryTime > CURRENT_TIMESTAMP")
    Optional<OtpCode> findValidOtp(String email, String otpCode, OtpType type);
}