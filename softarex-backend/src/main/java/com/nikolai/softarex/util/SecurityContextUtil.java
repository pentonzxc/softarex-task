package com.nikolai.softarex.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@UtilityClass
@Slf4j
public class SecurityContextUtil {

    public static UserDetails retrieveUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetails) authentication.getPrincipal();

    }
}
