package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.LoginCredentials;
import com.nikolai.softarex.dto.RegisterCredentials;
import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.model.User;
import org.springframework.stereotype.Component;

@Component
public class CredentialsUserMapper implements UserMapper {


    @Override
    public User userDtoToUser(UserDto dto) {
        var user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }


    public User loginCredentialsToUser(LoginCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        return user;
    }

    public User registerCredentialsToUser(RegisterCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        user.setLastName(credentials.getLastName());
        user.setFirstName(credentials.getFirstName());
        user.setPhoneNumber(credentials.getPhoneNumber());
        return user;
    }
}
