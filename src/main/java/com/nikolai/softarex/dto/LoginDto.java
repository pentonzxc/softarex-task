package com.nikolai.softarex.dto;


import lombok.Value;

@Value
public class LoginDto {
    String email;

    String password;

    boolean rememberMe;


    public boolean rememberMe() {
        return rememberMe;
    }
}
