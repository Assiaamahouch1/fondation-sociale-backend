package com.fondationsociale.auth.dto;

public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserGetDto user;
    
    // Constructors
    public AuthResponseDto() {}
    
    public AuthResponseDto(String accessToken, UserGetDto user) {
        this.accessToken = accessToken;
        this.user = user;
    }
    
    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public UserGetDto getUser() {
        return user;
    }
    
    public void setUser(UserGetDto user) {
        this.user = user;
    }
}