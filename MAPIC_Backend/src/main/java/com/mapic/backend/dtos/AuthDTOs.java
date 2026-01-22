package com.mapic.backend.dtos;

public class AuthDTOs {
    // DTO cho đăng ký
    public record RegisterRequest(String email, String password, String fullName, String avatarUrl) {}

    // DTO cho đăng nhập
    public record LoginRequest(String email, String password, String deviceName) {}
    
    // DTO trả về khi login thành công
    public record AuthResponse(String status, AuthData data) {}
    
    public record AuthData(String accessToken, UserData user) {}
    
    public record UserData(Long id, String fullName, String avatarUrl) {}
    
    // DTO response chung
    public record ApiResponse(String status, String message) {}
}