package com.authservice.repository;

import com.authservice.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    
    Optional<Token> findByTokenValue(String tokenValue);
    
    List<Token> findByUserId(Long userId);
    
    List<Token> findByUserIdAndTokenType(Long userId, Token.TokenType tokenType);
    
    @Query("SELECT t FROM Token t WHERE t.tokenValue = :tokenValue AND t.isRevoked = false AND t.isUsed = false AND t.expiryDate > :now")
    Optional<Token> findValidToken(@Param("tokenValue") String tokenValue, @Param("now") LocalDateTime now);
    
    @Query("SELECT t FROM Token t WHERE t.userId = :userId AND t.tokenType = :tokenType AND t.isRevoked = false AND t.expiryDate > :now")
    List<Token> findValidTokensByUserAndType(@Param("userId") Long userId, @Param("tokenType") Token.TokenType tokenType, @Param("now") LocalDateTime now);
    
    @Query("SELECT t FROM Token t WHERE t.expiryDate < :now")
    List<Token> findExpiredTokens(@Param("now") LocalDateTime now);
}