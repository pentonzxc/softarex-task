package com.nikolai.softarex.dto;

import lombok.Value;

@Value
public class LoginCredentials implements Credentials {

    String email;

    String password;

}
