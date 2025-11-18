package com.ubb.authService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ubb.authService.service.AuthRoleService;
import com.ubb.userModule.user.dto.RoleDto;
import com.ubb.userModule.user.role.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    protected AuthRoleService roleService;

    @PostMapping
    public ResponseEntity<?> addRole(@RequestBody RoleDto input) throws JsonProcessingException {
        return ResponseEntity.ok(roleService.updateRole(input));
    }

    @GetMapping
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable String roleType) {
        RoleType type = RoleType.valueOf(roleType);
        roleService.deleteRole(type);
        return ResponseEntity.ok("");
    }
}
