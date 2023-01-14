package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.LoginCredentials;
import com.nikolai.softarex.dto.RegisterCredentials;
import com.nikolai.softarex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialsMapperImpl implements CredentialsMapper {

    @Override
    public User registerCredentialsToUser(RegisterCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        user.setLastName(credentials.getLastName());
        user.setFirstName(credentials.getFirstName());
        user.setPhoneNumber(credentials.getPhoneNumber());
        return user;
    }

    @Override
    public User loginCredentialsToUser(LoginCredentials credentials) {
        var user = new User();
        user.setEmail(credentials.getEmail());
        user.setPassword(credentials.getPassword());
        return user;
    }
}
