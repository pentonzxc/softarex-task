package com.nikolai.softarex.dto;


import lombok.Value;

@Value
public class LoginDto {
    String email;

    String password;

    Boolean rememberMe;

}
