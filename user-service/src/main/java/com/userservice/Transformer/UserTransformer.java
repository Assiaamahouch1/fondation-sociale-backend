package com.userservice.Transformer;

import com.userservice.dto.get.UserGetDto;
import com.userservice.dto.get.RoleGetDto;
import com.userservice.dto.post.UserPostDto;
import com.userservice.dto.Security.UserSecurityDto;
import com.userservice.entity.User;
import com.userservice.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserTransformer {
    
    public UserGetDto entityToGetDto(User user) {
        if (user == null) {
            return null;
        }
        
        UserGetDto dto = new UserGetDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        
        if (user.getRoles() != null) {
            Set<RoleGetDto> roleDtos = user.getRoles().stream()
                .map(this::roleEntityToGetDto)
                .collect(Collectors.toSet());
            dto.setRoles(roleDtos);
        }
        
        return dto;
    }
    
    public User postDtoToEntity(UserPostDto dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        
        return user;
    }
    
    public UserSecurityDto entityToSecurityDto(User user) {
        if (user == null) {
            return null;
        }
        
        Set<String> roleNames = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet());
            
        return new UserSecurityDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getIsActive(),
            roleNames
        );
    }
    
    public void updateEntityFromPostDto(User user, UserPostDto dto) {
        if (user == null || dto == null) {
            return;
        }
        
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());
        user.setDateOfBirth(dto.getDateOfBirth());
        
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }
    }
    
    private RoleGetDto roleEntityToGetDto(Role role) {
        if (role == null) {
            return null;
        }
        
        RoleGetDto dto = new RoleGetDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setDescription(role.getDescription());
        dto.setCreatedAt(role.getCreatedAt());
        dto.setUpdatedAt(role.getUpdatedAt());
        
        return dto;
    }
}