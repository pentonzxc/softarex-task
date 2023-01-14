package com.nikolai.softarex.dto;

import lombok.Value;

@Value
public class RegisterCredentials implements Credentials {

    String email;

    String password;

    String firstName;

    String lastName;

    String phoneNumber;
}
