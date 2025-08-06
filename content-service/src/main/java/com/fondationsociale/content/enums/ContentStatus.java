package com.fondationsociale.content.enums;

public enum ContentStatus {
    DRAFT("DRAFT"),
    PUBLISHED("PUBLISHED"),
    ARCHIVED("ARCHIVED"),
    PENDING_REVIEW("PENDING_REVIEW");
    
    private final String value;
    
    ContentStatus(String value) {
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