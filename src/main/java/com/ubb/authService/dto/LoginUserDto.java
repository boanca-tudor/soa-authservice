package com.ubb.authService.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {
    protected String email;

    protected String password;
}
