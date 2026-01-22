package com.mapic.backend.repository;

import com.mapic.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<User, Long>: Quản lý bảng User, khóa chính kiểu Long
public interface UserRepository extends JpaRepository<User, Long> {
    // Tự động sinh câu lệnh: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // Tự động sinh câu lệnh: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);
}