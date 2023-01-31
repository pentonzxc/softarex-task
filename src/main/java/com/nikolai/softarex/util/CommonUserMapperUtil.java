package com.nikolai.softarex.util;


import com.nikolai.softarex.dto.ProfileDto;
import com.nikolai.softarex.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUserMapperUtil {

    public ProfileDto userEntityToProfileDto(final User user){
        return new ProfileDto(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber()
        );
    }
}
