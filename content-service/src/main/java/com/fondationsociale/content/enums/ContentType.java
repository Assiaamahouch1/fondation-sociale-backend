package com.fondationsociale.content.enums;

public enum ContentType {
    ARTICLE("ARTICLE"),
    EVENT("EVENT"),
    ANNOUNCEMENT("ANNOUNCEMENT"),
    NEWS("NEWS"),
    DOCUMENT("DOCUMENT");
    
    private final String value;
    
    ContentType(String value) {
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