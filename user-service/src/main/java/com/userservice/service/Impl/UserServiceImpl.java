package com.userservice.service.Impl;

import com.userservice.dto.get.UserGetDto;
import com.userservice.dto.post.UserPostDto;
import com.userservice.dto.Security.UserSecurityDto;
import com.userservice.entity.User;
import com.userservice.entity.Role;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.exception.BadRequestException;
import com.userservice.repository.UserRepository;
import com.userservice.repository.RoleRepository;
import com.userservice.service.facade.UserService;
import com.userservice.Transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserTransformer userTransformer;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional(readOnly = true)
    public List<UserGetDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserGetDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userTransformer::entityToGetDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserGetDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userTransformer::entityToGetDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserGetDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userTransformer::entityToGetDto);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<UserSecurityDto> getUserSecurityByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userTransformer::entityToSecurityDto);
    }
    
    @Override
    public UserGetDto createUser(UserPostDto userPostDto) {
        if (existsByUsername(userPostDto.getUsername())) {
            throw new BadRequestException("Username already exists: " + userPostDto.getUsername());
        }
        
        if (existsByEmail(userPostDto.getEmail())) {
            throw new BadRequestException("Email already exists: " + userPostDto.getEmail());
        }
        
        User user = userTransformer.postDtoToEntity(userPostDto);
        user.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
        
        // Assign roles if specified
        if (userPostDto.getRoleIds() != null && !userPostDto.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : userPostDto.getRoleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));
                roles.add(role);
            }
            user.setRoles(roles);
        } else {
            // Assign default ADHERENT role
            Role adherentRole = roleRepository.findByName("ADHERENT")
                    .orElseThrow(() -> new ResourceNotFoundException("Default ADHERENT role not found"));
            user.setRoles(Set.of(adherentRole));
        }
        
        User savedUser = userRepository.save(user);
        return userTransformer.entityToGetDto(savedUser);
    }
    
    @Override
    public UserGetDto updateUser(Long id, UserPostDto userPostDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        
        // Check if username is being changed and if it's already taken
        if (!existingUser.getUsername().equals(userPostDto.getUsername()) && 
            existsByUsername(userPostDto.getUsername())) {
            throw new BadRequestException("Username already exists: " + userPostDto.getUsername());
        }
        
        // Check if email is being changed and if it's already taken
        if (!existingUser.getEmail().equals(userPostDto.getEmail()) && 
            existsByEmail(userPostDto.getEmail())) {
            throw new BadRequestException("Email already exists: " + userPostDto.getEmail());
        }
        
        userTransformer.updateEntityFromPostDto(existingUser, userPostDto);
        
        if (userPostDto.getPassword() != null && !userPostDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
        }
        
        User savedUser = userRepository.save(existingUser);
        return userTransformer.entityToGetDto(savedUser);
    }
    
    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserGetDto> getActiveUsers() {
        return userRepository.findByIsActiveTrue()
                .stream()
                .map(userTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserGetDto> getInactiveUsers() {
        return userRepository.findByIsActiveFalse()
                .stream()
                .map(userTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserGetDto> getUsersByRole(String roleName) {
        return userRepository.findByRoleName(roleName)
                .stream()
                .map(userTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserGetDto> searchUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userTransformer::entityToGetDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Override
    public UserGetDto activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setIsActive(true);
        User savedUser = userRepository.save(user);
        return userTransformer.entityToGetDto(savedUser);
    }
    
    @Override
    public UserGetDto deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setIsActive(false);
        User savedUser = userRepository.save(user);
        return userTransformer.entityToGetDto(savedUser);
    }
    
    @Override
    public UserGetDto assignRolesToUser(Long userId, List<Long> roleIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));
            roles.add(role);
        }
        
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userTransformer.entityToGetDto(savedUser);
    }
}