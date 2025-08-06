package com.userservice.service.facade;

import com.userservice.dto.get.UserGetDto;
import com.userservice.dto.post.UserPostDto;
import com.userservice.dto.Security.UserSecurityDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<UserGetDto> getAllUsers();
    
    Optional<UserGetDto> getUserById(Long id);
    
    Optional<UserGetDto> getUserByUsername(String username);
    
    Optional<UserGetDto> getUserByEmail(String email);
    
    Optional<UserSecurityDto> getUserSecurityByEmail(String email);
    
    UserGetDto createUser(UserPostDto userPostDto);
    
    UserGetDto updateUser(Long id, UserPostDto userPostDto);
    
    void deleteUser(Long id);
    
    List<UserGetDto> getActiveUsers();
    
    List<UserGetDto> getInactiveUsers();
    
    List<UserGetDto> getUsersByRole(String roleName);
    
    List<UserGetDto> searchUsersByName(String name);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    UserGetDto activateUser(Long id);
    
    UserGetDto deactivateUser(Long id);
    
    UserGetDto assignRolesToUser(Long userId, List<Long> roleIds);
}