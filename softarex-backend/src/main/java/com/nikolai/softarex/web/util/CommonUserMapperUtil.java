package com.nikolai.softarex.web.util;


import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.web.dto.ProfileDto;
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
