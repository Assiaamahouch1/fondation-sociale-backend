package com.authservice.client;

import com.authservice.dto.Security.UserSecurityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserServiceClient {
    
    @GetMapping("/security/email/{email}")
    UserSecurityDto getUserSecurityByEmail(@PathVariable String email);
}