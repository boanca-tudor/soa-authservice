package com.ubb.authService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ubb.userModule.user.dto.AddRoleToUserDto;
import com.ubb.authService.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("userrole")
public class UserController {
    @Autowired
    protected AuthUserService userService;

    @PostMapping
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserDto input) throws JsonProcessingException {
        return ResponseEntity.ok(userService.addRoleToUser(input));
    }
}
