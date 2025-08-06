package com.fondationsociale.auth.controller;

import com.fondationsociale.auth.dto.*;
import com.fondationsociale.auth.enums.UserRole;
import com.fondationsociale.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        try {
            AuthResponseDto authResponse = authService.login(loginDto);
            return ResponseEntity.ok(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserPostDto userPostDto) {
        try {
            AuthResponseDto authResponse = authService.register(userPostDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserGetDto> getCurrentUser() {
        Optional<UserGetDto> currentUser = authService.getCurrentUser();
        return currentUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(isValid);
    }
}

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
class UserController {
    
    private final AuthService authService;
    
    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        List<UserGetDto> users = authService.findAll();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id) {
        Optional<UserGetDto> user = authService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserGetDto> createUser(@Valid @RequestBody UserPostDto userPostDto) {
        try {
            UserGetDto savedUser = authService.save(userPostDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable Long id, 
                                               @Valid @RequestBody UserPostDto userPostDto) {
        Optional<UserGetDto> updatedUser = authService.update(id, userPostDto);
        return updatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = authService.deleteById(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserGetDto>> getUsersByRole(@PathVariable UserRole role) {
        List<UserGetDto> users = authService.findByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/active/{isActive}")
    public ResponseEntity<List<UserGetDto>> getUsersByActiveStatus(@PathVariable Boolean isActive) {
        List<UserGetDto> users = authService.findByIsActive(isActive);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<UserGetDto> activateUser(@PathVariable Long id) {
        Optional<UserGetDto> activatedUser = authService.activateUser(id);
        return activatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserGetDto> deactivateUser(@PathVariable Long id) {
        Optional<UserGetDto> deactivatedUser = authService.deactivateUser(id);
        return deactivatedUser.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount() {
        long count = authService.count();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> getUserCountByRole(@PathVariable UserRole role) {
        long count = authService.countByRole(role);
        return ResponseEntity.ok(count);
    }
}