package com.userservice.controller;

import com.userservice.dto.get.RoleGetDto;
import com.userservice.dto.post.RolePostDto;
import com.userservice.service.facade.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RoleController {
    
    @Autowired
    private RoleService roleService;
    
    @GetMapping
    public ResponseEntity<List<RoleGetDto>> getAllRoles() {
        List<RoleGetDto> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RoleGetDto> getRoleById(@PathVariable Long id) {
        Optional<RoleGetDto> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<RoleGetDto> getRoleByName(@PathVariable String name) {
        Optional<RoleGetDto> role = roleService.getRoleByName(name);
        return role.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<RoleGetDto> createRole(@Valid @RequestBody RolePostDto rolePostDto) {
        RoleGetDto createdRole = roleService.createRole(rolePostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoleGetDto> updateRole(@PathVariable Long id, 
                                                 @Valid @RequestBody RolePostDto rolePostDto) {
        RoleGetDto updatedRole = roleService.updateRole(id, rolePostDto);
        return ResponseEntity.ok(updatedRole);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> existsByName(@PathVariable String name) {
        boolean exists = roleService.existsByName(name);
        return ResponseEntity.ok(exists);
    }
}