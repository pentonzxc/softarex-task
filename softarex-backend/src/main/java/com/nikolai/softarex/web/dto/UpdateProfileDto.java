package com.nikolai.softarex.web.dto;


import lombok.Value;

@Value
public class UpdateProfileDto {
    private String oldEmail;

    private String newEmail;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}
