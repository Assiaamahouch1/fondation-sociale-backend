package com.userservice.service.facade;

import com.userservice.dto.get.RoleGetDto;
import com.userservice.dto.post.RolePostDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    
    List<RoleGetDto> getAllRoles();
    
    Optional<RoleGetDto> getRoleById(Long id);
    
    Optional<RoleGetDto> getRoleByName(String name);
    
    RoleGetDto createRole(RolePostDto rolePostDto);
    
    RoleGetDto updateRole(Long id, RolePostDto rolePostDto);
    
    void deleteRole(Long id);
    
    boolean existsByName(String name);
}