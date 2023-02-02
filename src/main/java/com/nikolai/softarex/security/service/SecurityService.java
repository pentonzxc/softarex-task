package com.nikolai.softarex.security.service;


import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.domain.interfaces.UserService;
import com.nikolai.softarex.security.util.CookieUtil;
import com.nikolai.softarex.security.util.SecurityContextUtil;
import com.nikolai.softarex.web.dto.LoginDto;
import com.nikolai.softarex.web.exception.*;
import com.nikolai.softarex.web.service.EmailService;
import com.nikolai.softarex.web.util.ExceptionMessageUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.nikolai.softarex.web.util.ExceptionMessageUtil.emailNotFoundMsg;


@Component
@Slf4j
public class SecurityService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService.JwtServiceHelper jwtHelper;

    private final EmailService emailService;


    @Value("${cookies.domain}")
    private String domain;


    @Autowired
    public SecurityService(UserService userService,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService, EmailService emailService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtService.new JwtServiceHelper();
        this.emailService = emailService;
    }


    public void register(User user, HttpServletRequest emailRequest)
            throws UserAlreadyExistException, MessagingException {
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


    public ResponseCookie[] login(LoginDto dto) {
        var authentication = authenticate(dto);

        var tokens = jwtHelper.createRefreshAndAccessToken((UserDetails) authentication.getPrincipal());
        if (dto.rememberMe()) {
            return CookieUtil.createJwtCookies(tokens, domain);
        }

        return new ResponseCookie[]{
                CookieUtil.createJwtCookie(tokens[0], domain)
        };
    }


    public Optional<ResponseCookie> verifyIfRequireUpdateToken(UserDetails user, String token, String refreshToken) {
        if (token == null && refreshToken != null) {
            return Optional.of(
                    CookieUtil.createJwtCookie(jwtHelper.createRefreshAndAccessToken(user)[0], domain)
            );
        }

        return Optional.empty();
    }

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

    public User authenticatedUser() {
        var userDetails = SecurityContextUtil.retrieveUserDetails();
        return userService.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
    }


}
