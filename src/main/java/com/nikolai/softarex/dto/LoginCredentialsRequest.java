package com.nikolai.softarex.dto;


import lombok.Value;

@Value
public class LoginCredentialsRequest {
    String email;

    String password;

    Boolean rememberMe;

}
