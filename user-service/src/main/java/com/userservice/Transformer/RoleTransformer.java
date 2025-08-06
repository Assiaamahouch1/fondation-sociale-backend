package com.userservice.Transformer;

import com.userservice.dto.get.RoleGetDto;
import com.userservice.dto.post.RolePostDto;
import com.userservice.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleTransformer {
    
    public RoleGetDto entityToGetDto(Role role) {
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
    
    public Role postDtoToEntity(RolePostDto dto) {
        if (dto == null) {
            return null;
        }
        
        Role role = new Role();
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        
        return role;
    }
    
    public void updateEntityFromPostDto(Role role, RolePostDto dto) {
        if (role == null || dto == null) {
            return;
        }
        
        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
    }
}