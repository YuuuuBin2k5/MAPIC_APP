package com.mapic.backend.repository;

import com.mapic.backend.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    @Query("SELECT t FROM Token t WHERE t.token = :token AND t.isActive = true AND t.expiresAt > CURRENT_TIMESTAMP")
    Optional<Token> findValidToken(String token);
    
    Optional<Token> findByToken(String token);
}
