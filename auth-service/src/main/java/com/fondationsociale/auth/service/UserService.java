package com.fondationsociale.auth.service;

import com.fondationsociale.auth.dto.UserGetDto;
import com.fondationsociale.auth.dto.UserPostDto;
import com.fondationsociale.auth.entity.User;
import com.fondationsociale.auth.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<UserGetDto> findAll();
    
    Optional<UserGetDto> findById(Long id);
    
    UserGetDto save(UserPostDto userPostDto);
    
    Optional<UserGetDto> update(Long id, UserPostDto userPostDto);
    
    boolean deleteById(Long id);
    
    boolean existsById(Long id);
    
    long count();
    
    Optional<UserGetDto> findByUsername(String username);
    
    Optional<UserGetDto> findByEmail(String email);
    
    Optional<UserGetDto> findByUsernameOrEmail(String usernameOrEmail);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    List<UserGetDto> findByRole(UserRole role);
    
    List<UserGetDto> findByIsActive(Boolean isActive);
    
    List<UserGetDto> findByRoleAndIsActive(UserRole role, Boolean isActive);
    
    long countByRole(UserRole role);
    
    Optional<UserGetDto> activateUser(Long id);
    
    Optional<UserGetDto> deactivateUser(Long id);
}