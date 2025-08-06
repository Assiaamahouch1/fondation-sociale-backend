package com.userservice.controller;

import com.userservice.dto.get.UserGetDto;
import com.userservice.dto.post.UserPostDto;
import com.userservice.service.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAllUsers() {
        List<UserGetDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long id) {
        Optional<UserGetDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<UserGetDto> getUserByUsername(@PathVariable String username) {
        Optional<UserGetDto> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<UserGetDto> getUserByEmail(@PathVariable String email) {
        Optional<UserGetDto> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserGetDto> createUser(@Valid @RequestBody UserPostDto userPostDto) {
        UserGetDto createdUser = userService.createUser(userPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserGetDto> updateUser(@PathVariable Long id, 
                                                 @Valid @RequestBody UserPostDto userPostDto) {
        UserGetDto updatedUser = userService.updateUser(id, userPostDto);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<UserGetDto>> getActiveUsers() {
        List<UserGetDto> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/inactive")
    public ResponseEntity<List<UserGetDto>> getInactiveUsers() {
        List<UserGetDto> users = userService.getInactiveUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserGetDto>> getUsersByRole(@PathVariable String roleName) {
        List<UserGetDto> users = userService.getUsersByRole(roleName);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<UserGetDto>> searchUsersByName(@RequestParam String name) {
        List<UserGetDto> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<UserGetDto> activateUser(@PathVariable Long id) {
        UserGetDto user = userService.activateUser(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<UserGetDto> deactivateUser(@PathVariable Long id) {
        UserGetDto user = userService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("/{userId}/roles")
    public ResponseEntity<UserGetDto> assignRolesToUser(@PathVariable Long userId, 
                                                        @RequestBody List<Long> roleIds) {
        UserGetDto user = userService.assignRolesToUser(userId, roleIds);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
}