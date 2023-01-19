package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, UserDto> {


    @Override
    public User convertDtoToEntity(UserDto dto) {
        var user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setLastName(dto.getLastName());
        user.setFirstName(dto.getFirstName());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

    @Override
    public UserDto convertEntityToDto(User entity) {
        return null;
    }
}
