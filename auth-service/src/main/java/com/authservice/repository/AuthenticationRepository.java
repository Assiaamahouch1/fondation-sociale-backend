package com.authservice.repository;

import com.authservice.entity.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
    
    Optional<Authentication> findByUserId(Long userId);
    
    Optional<Authentication> findByEmail(String email);
    
    Boolean existsByUserId(Long userId);
    
    Boolean existsByEmail(String email);
}