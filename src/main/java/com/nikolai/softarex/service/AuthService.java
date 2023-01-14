package com.nikolai.softarex.service;


import com.nikolai.softarex.dto.Credentials;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AuthService {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    @Autowired
    public AuthService(UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(User user) throws UserAlreadyExistException {
        var email = user.getEmail();

        if (!(userService.isEmailAvailable(email))) {
            throw new UserAlreadyExistException("User with email " + email + " already exist");
        }
        var encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        userService.save(user);
    }


    public Authentication authenticate(Credentials credentials) throws AuthenticationException {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getEmail(), credentials.getPassword()
        ));
    }


    public String[] createTokens(UserDetails userDetails) {
        String token = jwtService.createToken(userDetails);
        String refreshToken = jwtService.createRefreshToken(userDetails);
        return new String[]{
                token, refreshToken
        };
    }

}
