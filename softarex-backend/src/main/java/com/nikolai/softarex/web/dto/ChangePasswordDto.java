package com.nikolai.softarex.web.dto;


import lombok.Value;

@Value
public class ChangePasswordDto {
    private String oldPassword;

    private String newPassword;

}
