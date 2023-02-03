package com.nikolai.softarex.web.dto;


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
