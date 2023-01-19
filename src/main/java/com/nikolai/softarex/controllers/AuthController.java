package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.LoginCredentialsRequest;
import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
public class AuthController {

    private final EntityMapper<User, UserDto> userMapper;

    private final AuthService authService;

    @Value("${cookies.domain}")
    private String domain;

    @Autowired
    public AuthController(@Qualifier("userMapper") EntityMapper<User, UserDto> userMapper,
                          AuthService authService) {
        this.userMapper = userMapper;
        this.authService = authService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody UserDto userDto,
                                      HttpServletRequest request
    ) throws UserAlreadyExistException, MessagingException {
        var user = userMapper.convertDtoToEntity(userDto);
        authService.register(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/register/verify", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyRegister(@RequestParam(name = "code", required = true)
                                            String verificationCode) {
        authService.verifyRegister(verificationCode);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody
                                   LoginCredentialsRequest credentials) {
        var authentication = authService.authenticate(credentials);

        var tokens = authService.createTokens((UserDetails) authentication.getPrincipal());
        var cookies = AuthService.createJwtCookies(tokens, domain);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookies[0].toString(), cookies[1].toString())
                .build();
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> validateToken(@CookieValue(name = "token", required = false) String token,
                                           @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        var user = authService.retrieveUserDetails();
        log.debug("validate refresh token - email - {}", user.getUsername());

        boolean validate = authService.validateToken(Optional.ofNullable(token), user);

        if (!validate) {
            var tokens = authService.refreshTokens(user);
            var cookies = AuthService.createJwtCookies(tokens, domain);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookies[0].toString(), cookies[1].toString())
                    .build();
        }
        return ResponseEntity.ok().build();
    }


}
