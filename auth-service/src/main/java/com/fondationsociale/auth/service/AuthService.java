package com.fondationsociale.auth.service;

import com.fondationsociale.auth.dto.AuthResponseDto;
import com.fondationsociale.auth.dto.LoginDto;
import com.fondationsociale.auth.dto.UserGetDto;
import com.fondationsociale.auth.dto.UserPostDto;

import java.util.Optional;

public interface AuthService extends UserService {
    
    AuthResponseDto login(LoginDto loginDto);
    
    AuthResponseDto register(UserPostDto userPostDto);
    
    Optional<UserGetDto> getCurrentUser();
    
    boolean validateToken(String token);
    
    String generateToken(UserGetDto user);
}