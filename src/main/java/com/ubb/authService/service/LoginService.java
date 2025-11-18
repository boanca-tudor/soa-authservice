package com.ubb.authService.service;

import com.ubb.authService.dto.LoginUserDto;
import com.ubb.userModule.jwt.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    protected JwtProvider jwtProvider;

    public String authenticate(LoginUserDto input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return jwtProvider.generateToken(authentication);
    }
}
