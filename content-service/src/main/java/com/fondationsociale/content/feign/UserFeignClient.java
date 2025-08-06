package com.fondationsociale.content.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service", path = "/api/users")
public interface UserFeignClient {
    
    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") Long id);
    
    @GetMapping("/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);
}

class UserDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private Boolean isActive;
    
    // Constructors
    public UserDto() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}