package com.ubb.authService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ubb.authService.service.AuthUserService;
import com.ubb.userModule.user.dto.UserDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
@CrossOrigin
public class RegisterController {
    @Autowired
    protected AuthUserService userService;

    @PostMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDataDto registerInfo) throws JsonProcessingException {
        return ResponseEntity.ok(userService.updateUser(registerInfo));
    }
}
