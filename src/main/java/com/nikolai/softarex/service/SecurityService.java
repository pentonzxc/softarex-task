package com.nikolai.softarex.service;


import com.nikolai.softarex.dto.ChangePasswordRequest;
import com.nikolai.softarex.dto.LoginCredentialsRequest;
import com.nikolai.softarex.exception.*;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.util.CookieUtil;
import com.nikolai.softarex.util.ExceptionMessageUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.nikolai.softarex.util.ExceptionMessageUtil.emailNotFoundMsg;


@Component
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;


    @Autowired
    public SecurityService(UserService userService,
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
    public void register(User user, HttpServletRequest emailRequest) throws UserAlreadyExistException, MessagingException {
        var email = user.getEmail();

        if (!(userService.isEmailAvailable(email))) {
            throw new UserAlreadyExistException(ExceptionMessageUtil.userAlreadyExistMsg(email));
        }
        var verificationCode = RandomStringUtils.random(64);
        var encodePassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodePassword);
        user.setVerificationCode(verificationCode);
        user.setActive(false);

        userService.save(user);

        emailService.sendVerificationEmail(user, emailRequest.getRequestURL().toString());
    }

    // exception for invalid code
    public void verifyRegister(String verificationCode) throws InvalidVerificationCode {
        var userOpt = userService.findByVerificationCode(verificationCode);

        var user = userOpt.orElseThrow(VerificationCodeNotFoundException::new);
        user.setActive(true);

        userService.save(user);
    }


    public Authentication authenticate(LoginCredentialsRequest credentials) throws AuthenticationException,
            UserNotVerifyException {
        var email = credentials.getEmail();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, credentials.getPassword(), AuthorityUtils.NO_AUTHORITIES
        ));

        var user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));

        if (!user.isActive()) {
            throw new UserNotVerifyException();
        }
        return authentication;
    }


    public void changePassword(ChangePasswordRequest passwords, HttpServletRequest emailRequest) throws MessagingException {
        var email = passwords.getUserEmail();
        var user = userService.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));

        var oldPassword = passwordEncoder.encode(passwords.getOldPassword());

        if (!user.getPassword().equals(oldPassword)) {
            throw new BadCredentialsException("passwords doesn't match");
        }

        var newPassword = passwordEncoder.encode(passwords.getOldPassword());

        user.setPasswordChange(newPassword);

        emailService.sendUpdatePasswordEmail(user, emailRequest.getRequestURL().toString());
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


}
