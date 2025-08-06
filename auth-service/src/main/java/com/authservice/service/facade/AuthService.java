package com.authservice.service.facade;

import com.authservice.dto.Security.AuthResponseDto;
import com.authservice.dto.Security.LoginRequestDto;
import com.authservice.dto.Security.RefreshTokenRequestDto;

public interface AuthService {
    
    AuthResponseDto login(LoginRequestDto loginRequest);
    
    AuthResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);
    
    void logout(String accessToken);
    
    void logoutAll(Long userId);
    
    boolean validateToken(String token);
    
    void revokeToken(String token);
}