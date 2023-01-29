package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, UserDto> {


    @Override
    public User convertDtoToEntity(UserDto dto) {
        var entity = new User();
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }

    @Override
    public UserDto convertEntityToDto(User entity) {
        return new UserDto(
                entity.getEmail(),
                entity.getPassword(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhoneNumber()
        );
    }
}
