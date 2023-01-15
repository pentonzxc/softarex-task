package com.nikolai.softarex.service;


import com.nikolai.softarex.exception.InvalidVerificationCode;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.exception.UserNotVerifyException;
import com.nikolai.softarex.interfaces.Credentials;
import com.nikolai.softarex.model.User;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
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

    private final JwtService jwtService;

    private final EmailService emailService;


    @Autowired
    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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


    public Authentication authenticate(Credentials credentials) throws AuthenticationException,
                                                                UserNotVerifyException {
        var email = credentials.getEmail();
        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, credentials.getPassword()
        ));

        var user = userService.findByEmail(email).get();

        if (!user.isActive()) {
            throw new UserNotVerifyException();
        }
        return authentication;
    }


    public String[] createTokens(UserDetails userDetails) {
        String token = jwtService.createToken(userDetails);
        String refreshToken = jwtService.createRefreshToken(userDetails);
        return new String[]{
                token, refreshToken
        };
    }

}
