package com.nikolai.softarex.mapper;


import com.nikolai.softarex.dto.LoginCredentials;
import com.nikolai.softarex.dto.RegisterCredentials;
import com.nikolai.softarex.model.User;


public interface CredentialsMapper {

    User registerCredentialsToUser(RegisterCredentials credentials);

    User loginCredentialsToUser(LoginCredentials credentials);

}
