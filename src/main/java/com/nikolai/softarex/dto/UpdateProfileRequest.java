package com.nikolai.softarex.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    private String oldEmail;

    private String newEmail;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
