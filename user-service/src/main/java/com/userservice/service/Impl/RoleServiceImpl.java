package com.userservice.service.Impl;

import com.userservice.dto.get.RoleGetDto;
import com.userservice.dto.post.RolePostDto;
import com.userservice.entity.Role;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.exception.BadRequestException;
import com.userservice.repository.RoleRepository;
import com.userservice.service.facade.RoleService;
import com.userservice.Transformer.RoleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private RoleTransformer roleTransformer;
    
    @Override
    @Transactional(readOnly = true)
    public List<RoleGetDto> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleGetDto> getRoleById(Long id) {
        return roleRepository.findById(id)
                .map(roleTransformer::entityToGetDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoleGetDto> getRoleByName(String name) {
        return roleRepository.findByName(name)
                .map(roleTransformer::entityToGetDto);
    }
    
    @Override
    public RoleGetDto createRole(RolePostDto rolePostDto) {
        if (existsByName(rolePostDto.getName())) {
            throw new BadRequestException("Role already exists with name: " + rolePostDto.getName());
        }
        
        Role role = roleTransformer.postDtoToEntity(rolePostDto);
        Role savedRole = roleRepository.save(role);
        return roleTransformer.entityToGetDto(savedRole);
    }
    
    @Override
    public RoleGetDto updateRole(Long id, RolePostDto rolePostDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        
        // Check if name is being changed and if it's already taken
        if (!existingRole.getName().equals(rolePostDto.getName()) && 
            existsByName(rolePostDto.getName())) {
            throw new BadRequestException("Role already exists with name: " + rolePostDto.getName());
        }
        
        roleTransformer.updateEntityFromPostDto(existingRole, rolePostDto);
        Role savedRole = roleRepository.save(existingRole);
        return roleTransformer.entityToGetDto(savedRole);
    }
    
    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        roleRepository.delete(role);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}