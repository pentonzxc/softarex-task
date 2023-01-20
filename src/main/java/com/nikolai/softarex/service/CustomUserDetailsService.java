package com.nikolai.softarex.service;

import com.nikolai.softarex.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;


    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOpt = userService.findByEmail(email);
        log.debug("Retrieve user with email {}", email);

        if (userOpt.isEmpty()) {
            log.debug("User with email {} don't exist", email);
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }

        return userOpt.get();
    }
}
