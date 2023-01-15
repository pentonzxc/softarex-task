package com.nikolai.softarex.dto;


import com.nikolai.softarex.interfaces.Credentials;
import lombok.Value;

@Value
public class LoginCredentials implements Credentials {
    String email;

    String password;
}
