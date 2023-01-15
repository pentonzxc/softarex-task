package com.nikolai.softarex.mapper;

import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.model.User;

public interface UserMapper {
    public User userDtoToUser(UserDto dto);
}
