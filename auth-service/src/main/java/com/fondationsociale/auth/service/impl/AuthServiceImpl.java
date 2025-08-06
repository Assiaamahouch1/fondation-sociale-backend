package com.fondationsociale.auth.service.impl;

import com.fondationsociale.auth.dto.AuthResponseDto;
import com.fondationsociale.auth.dto.LoginDto;
import com.fondationsociale.auth.dto.UserGetDto;
import com.fondationsociale.auth.dto.UserPostDto;
import com.fondationsociale.auth.entity.User;
import com.fondationsociale.auth.enums.UserRole;
import com.fondationsociale.auth.service.AuthService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey jwtSecret;
    private final long jwtExpirationInMs;
    
    @Autowired
    public AuthServiceImpl(UserServiceImpl userService,
                          PasswordEncoder passwordEncoder,
                          @Value("${app.jwt.secret:mySecretKey}") String jwtSecret,
                          @Value("${app.jwt.expiration:86400000}") long jwtExpirationInMs) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtSecret = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationInMs = jwtExpirationInMs;
    }
    
    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        Optional<UserGetDto> userOptional = userService.findByUsernameOrEmail(loginDto.getUsernameOrEmail());
        
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with username or email: " + loginDto.getUsernameOrEmail());
        }
        
        UserGetDto user = userOptional.get();
        
        if (!user.getIsActive()) {
            throw new RuntimeException("User account is deactivated");
        }
        
        String token = generateToken(user);
        return new AuthResponseDto(token, user);
    }
    
    @Override
    public AuthResponseDto register(UserPostDto userPostDto) {
        if (userService.existsByUsername(userPostDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        
        if (userService.existsByEmail(userPostDto.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }
        
        UserGetDto savedUser = userService.save(userPostDto);
        String token = generateToken(savedUser);
        
        return new AuthResponseDto(token, savedUser);
    }
    
    @Override
    public Optional<UserGetDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            return userService.findByUsername(username);
        }
        return Optional.empty();
    }
    
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    @Override
    public String generateToken(UserGetDto user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().getValue())
                .signWith(jwtSecret, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }
    
    // Delegate methods to UserService
    public List<UserGetDto> findAll() {
        return userService.findAll();
    }
    
    public Optional<UserGetDto> findById(Long id) {
        return userService.findById(id);
    }
    
    public UserGetDto save(UserPostDto userPostDto) {
        return userService.save(userPostDto);
    }
    
    public Optional<UserGetDto> update(Long id, UserPostDto userPostDto) {
        return userService.update(id, userPostDto);
    }
    
    public boolean deleteById(Long id) {
        return userService.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return userService.existsById(id);
    }
    
    public long count() {
        return userService.count();
    }
    
    public Optional<UserGetDto> findByUsername(String username) {
        return userService.findByUsername(username);
    }
    
    public Optional<UserGetDto> findByEmail(String email) {
        return userService.findByEmail(email);
    }
    
    public Optional<UserGetDto> findByUsernameOrEmail(String usernameOrEmail) {
        return userService.findByUsernameOrEmail(usernameOrEmail);
    }
    
    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }
    
    public List<UserGetDto> findByRole(UserRole role) {
        return userService.findByRole(role);
    }
    
    public List<UserGetDto> findByIsActive(Boolean isActive) {
        return userService.findByIsActive(isActive);
    }
    
    public List<UserGetDto> findByRoleAndIsActive(UserRole role, Boolean isActive) {
        return userService.findByRoleAndIsActive(role, isActive);
    }
    
    public long countByRole(UserRole role) {
        return userService.countByRole(role);
    }
    
    public Optional<UserGetDto> activateUser(Long id) {
        return userService.activateUser(id);
    }
    
    public Optional<UserGetDto> deactivateUser(Long id) {
        return userService.deactivateUser(id);
    }
}