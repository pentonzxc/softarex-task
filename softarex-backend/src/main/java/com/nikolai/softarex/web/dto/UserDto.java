package com.nikolai.softarex.web.dto;


import lombok.Value;

@Value
public class UserDto  {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
