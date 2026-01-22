package com.mapic.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    // Mapping: cột otp_code -> biến otpCode
    @Column(name = "otp_code", nullable = false, length = 6)
    private String otpCode;

    // QUAN TRỌNG: Mapping Enum vào cột VARCHAR
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType type;

    // Mapping: cột expiry_time -> biến expiryTime
    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "is_used")
    @Builder.Default
    private Boolean isUsed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}