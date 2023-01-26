package com.nikolai.softarex.service;


import com.nikolai.softarex.dto.ChangePasswordDto;
import com.nikolai.softarex.dto.LoginDto;
import com.nikolai.softarex.exception.*;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.util.ExceptionMessageUtil;
import com.nikolai.softarex.util.SecurityContextUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.nikolai.softarex.util.ExceptionMessageUtil.emailNotFoundMsg;


@Component
@Slf4j
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;


    @Autowired
    public SecurityService(UserService userService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }



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

        log.debug("User with email - {} , waiting verify ", email);

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


    public Authentication authenticate(LoginDto credentials) throws AuthenticationException,
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


    public void changePassword(ChangePasswordDto passwords, HttpServletRequest emailRequest) throws MessagingException {
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


    public User authenticatedUser(){
        var userDetails = SecurityContextUtil.retrieveUserDetails();
        return userService.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
    }
}
