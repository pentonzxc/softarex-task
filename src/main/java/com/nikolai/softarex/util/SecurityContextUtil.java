package com.nikolai.softarex.util;


import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@UtilityClass
@Slf4j
public class SecurityContextUtil {

    public static UserDetails retrieveUserDetails() {
        var authentication = SecurityContextUtil.contextAuthentication();

        return (UserDetails) authentication.getPrincipal();
    }

    public static void authenticateRequest(UserDetails principal,
                                                     Object credentials,
                                                     List<? extends GrantedAuthority> authorities){
        var authentication = new UsernamePasswordAuthenticationToken(
                principal, credentials, authorities
        );

        SecurityContextUtil.context().setAuthentication(authentication);
    }


    public Authentication contextAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public SecurityContext context(){
        return SecurityContextHolder.getContext();
    }

}
