package com.nikolai.softarex.dto;


import lombok.*;

@Value
public class UpdateProfileDto {
    private String oldEmail;

    private String newEmail;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
