package com.ubb.authService.controller;

import com.ubb.authService.dto.JwtDto;
import com.ubb.authService.dto.LoginUserDto;
import com.ubb.authService.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@CrossOrigin
public class LoginController {
    @Autowired
    protected LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginUserDto input) {
        String jwt = loginService.authenticate(input);

        return ResponseEntity.ok(JwtDto.builder().token(jwt).build());
    }
}
