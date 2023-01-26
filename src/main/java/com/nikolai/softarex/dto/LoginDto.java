package com.nikolai.softarex.dto;


import lombok.Data;
import lombok.Value;

@Value
public class LoginDto {
    private String email;

    private String password;

    private boolean rememberMe;

    public boolean rememberMe(){
        return rememberMe;
    }

}
