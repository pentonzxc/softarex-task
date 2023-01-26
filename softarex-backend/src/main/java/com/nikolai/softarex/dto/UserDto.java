package com.nikolai.softarex.dto;


import lombok.*;

@Value
public class UserDto  {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
