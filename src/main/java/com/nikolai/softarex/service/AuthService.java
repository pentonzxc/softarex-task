package com.nikolai.softarex.service;


import com.nikolai.softarex.dto.LoginCredentialsRequest;
import com.nikolai.softarex.exception.InvalidVerificationCode;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.exception.UserNotVerifyException;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.util.CookieUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;


    @Autowired
    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService, AuthenticationManager authenticationManager,
                       EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }


    @Transactional
    public void register(User user, HttpServletRequest request) throws UserAlreadyExistException, MessagingException {
        var email = user.getEmail();

        if (!(userService.isEmailAvailable(email))) {
            throw new UserAlreadyExistException("User with email " + email + " already exist");
        }
        var verificationCode = RandomStringUtils.random(64);
        var encodePassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodePassword);
        user.setVerificationCode(verificationCode);
        user.setActive(false);

        userService.save(user);

        emailService.sendVerificationEmail(user, request.getRequestURL().toString());
    }

    // exception for invalid code
    public void verifyRegister(String verificationCode) throws InvalidVerificationCode {
        var userOpt = userService.findByVerificationCode(verificationCode);
        if (userOpt.isEmpty()) {
            throw new InvalidVerificationCode();
        }
        var user = userOpt.get();
        user.setActive(true);

        userService.update(user);
    }


    public Authentication authenticate(LoginCredentialsRequest credentials) throws AuthenticationException,
            UserNotVerifyException {
        var email = credentials.getEmail();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, credentials.getPassword(), AuthorityUtils.NO_AUTHORITIES
        ));

        var user = userService.findByEmail(email).get();

        if (!user.isActive()) {
            throw new UserNotVerifyException();
        }
        return authentication;
    }


    public boolean validateToken(Optional<String> token, UserDetails user) {
        return token.filter(s -> jwtService.validateToken(s, user)).isPresent();
    }

    public String[] refreshTokens(UserDetails user) {
        return jwtService.createTokens(user);
    }

    public String[] createTokens(UserDetails user) {
        return jwtService.createTokens(user);
    }


    public static ResponseCookie[] createJwtCookies(String[] tokens, String domain) {
        var jwtCookie = CookieUtil.createJwtCookie(tokens[0], domain);
        var refreshJwtCookie = CookieUtil.createRefreshJwtCookie(tokens[1], domain);

        return new ResponseCookie[]{
                jwtCookie, refreshJwtCookie
        };
    }
    
    public UserDetails retrieveUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }


}
