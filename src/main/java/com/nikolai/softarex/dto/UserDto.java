package com.nikolai.softarex.dto;


import com.nikolai.softarex.interfaces.Credentials;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Credentials {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String phoneNumber;

}
