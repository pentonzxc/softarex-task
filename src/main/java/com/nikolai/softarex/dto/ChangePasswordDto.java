package com.nikolai.softarex.dto;


import lombok.*;

@Value
public class ChangePasswordDto {
    private String oldPassword;

    private String newPassword;

}
