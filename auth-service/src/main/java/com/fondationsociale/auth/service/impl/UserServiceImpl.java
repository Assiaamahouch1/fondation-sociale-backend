package com.fondationsociale.auth.service.impl;

import com.fondationsociale.auth.dto.UserGetDto;
import com.fondationsociale.auth.dto.UserPostDto;
import com.fondationsociale.auth.entity.User;
import com.fondationsociale.auth.enums.UserRole;
import com.fondationsociale.auth.repository.UserRepository;
import com.fondationsociale.auth.service.UserService;
import com.fondationsociale.auth.transformer.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, 
                          UserTransformer userTransformer,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional(readOnly = true)
    public List<UserGetDto> findAll() {
        List<User> users = userRepository.findAll();
        return userTransformer.entitiesToGetDtos(users);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserGetDto> findById(Long id) {
        return userRepository.findById(id)
                .map(userTransformer::entityToGetDto);
    }
    
    public UserGetDto save(UserPostDto userPostDto) {
        User user = userTransformer.postDtoToEntity(userPostDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userTransformer.entityToGetDto(savedUser);
    }
    
    public Optional<UserGetDto> update(Long id, UserPostDto userPostDto) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    userTransformer.updateEntityFromPostDto(userPostDto, existingUser);
                    if (userPostDto.getPassword() != null) {
                        existingUser.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
                    }
                    User updatedUser = userRepository.save(existingUser);
                    return userTransformer.entityToGetDto(updatedUser);
                });
    }
    
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return userRepository.count();
    }
    
    @Transactional(readOnly = true)
    public Optional<UserGetDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userTransformer::entityToGetDto);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserGetDto> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userTransformer::entityToGetDto);
    }
    
    @Transactional(readOnly = true)
    public Optional<UserGetDto> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findByUsernameOrEmail(usernameOrEmail)
                .map(userTransformer::entityToGetDto);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    @Transactional(readOnly = true)
    public List<UserGetDto> findByRole(UserRole role) {
        List<User> users = userRepository.findByRole(role);
        return userTransformer.entitiesToGetDtos(users);
    }
    
    @Transactional(readOnly = true)
    public List<UserGetDto> findByIsActive(Boolean isActive) {
        List<User> users = userRepository.findByIsActive(isActive);
        return userTransformer.entitiesToGetDtos(users);
    }
    
    @Transactional(readOnly = true)
    public List<UserGetDto> findByRoleAndIsActive(UserRole role, Boolean isActive) {
        List<User> users = userRepository.findByRoleAndIsActive(role, isActive);
        return userTransformer.entitiesToGetDtos(users);
    }
    
    @Transactional(readOnly = true)
    public long countByRole(UserRole role) {
        return userRepository.countByRole(role);
    }
    
    public Optional<UserGetDto> activateUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsActive(true);
                    User updatedUser = userRepository.save(user);
                    return userTransformer.entityToGetDto(updatedUser);
                });
    }
    
    public Optional<UserGetDto> deactivateUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsActive(false);
                    User updatedUser = userRepository.save(user);
                    return userTransformer.entityToGetDto(updatedUser);
                });
    }
}