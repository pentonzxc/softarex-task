package com.nikolai.softarex.dto;


import lombok.*;

@Value
public class ChangePasswordDto {
    private String userEmail;

    private String oldPassword;

    private String newPassword;

}
