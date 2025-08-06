package com.fondationsociale.auth.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    ADHERENT("ADHERENT");
    
    private final String value;
    
    UserRole(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}