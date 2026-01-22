package com.mapic.backend.service;

import com.mapic.backend.dtos.AuthDTOs.*;
import com.mapic.backend.entity.*;
import com.mapic.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    // --- 1. ĐĂNG KÝ (Không OTP) ---
    @Transactional
    public ApiResponse register(RegisterRequest req) {
        // Kiểm tra email trùng
        if (userRepository.existsByEmail(req.email())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // Xử lý tên hiển thị
        String finalName = req.fullName();
        if (finalName == null || finalName.isEmpty()) {
            finalName = req.email().split("@")[0];
        }

        User user = User.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .fullName(finalName)
                .avatarUrl(req.avatarUrl())
                .isActive(true)  // Active ngay, không cần OTP
                .build();
        
        userRepository.save(user);

        return new ApiResponse("success", "Tài khoản đã được tạo.");
    }

    // --- 2. ĐĂNG NHẬP (Tạo Opaque Token) ---
    @Transactional
    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không đúng"));

        // Kiểm tra password
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng");
        }

        // Kiểm tra tài khoản đã active chưa
        if (!user.getIsActive()) {
            throw new RuntimeException("Tài khoản chưa được kích hoạt");
        }

        // Tạo Opaque Token (UUID)
        String tokenString = UUID.randomUUID().toString();
        
        Token token = Token.builder()
                .user(user)
                .token(tokenString)
                .deviceName(req.deviceName())
                .build();
        
        tokenRepository.save(token);

        // Trả về response theo đúng spec
        UserData userData = new UserData(
            user.getId(),
            user.getFullName(),
            user.getAvatarUrl()
        );
        
        AuthData authData = new AuthData(tokenString, userData);
        
        return new AuthResponse("success", authData);
    }

    // --- 3. ĐĂNG XUẤT ---
    @Transactional
    public void logout(String tokenString) {
        Token token = tokenRepository.findByToken(tokenString)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ"));
        
        // Vô hiệu hóa token
        token.setIsActive(false);
        tokenRepository.save(token);
    }

    // --- 4. XÁC THỰC TOKEN (Dùng cho các API khác) ---
    public User validateToken(String tokenString) {
        Token token = tokenRepository.findValidToken(tokenString)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ hoặc đã hết hạn"));
        
        return token.getUser();
    }
}
