package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.LoginDto;
import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.service.JwtService;
import com.nikolai.softarex.service.SecurityService;
import com.nikolai.softarex.util.CookieUtil;
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
@Slf4j
public class AuthController {

    private final EntityMapper<User, UserDto> userMapper;

    private final SecurityService securityService;

    private final JwtService.JwtServiceHelper jwtHelper;

    @Value("${cookies.domain}")
    private String domain;

    @Autowired
    public AuthController(@Qualifier("userMapper") EntityMapper<User, UserDto> userMapper,
                          SecurityService securityService,
                          JwtService jwtService) {
        this.userMapper = userMapper;
        this.securityService = securityService;
        this.jwtHelper = jwtService.new JwtServiceHelper();
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody UserDto userDto,
                                      HttpServletRequest request
    ) throws UserAlreadyExistException, MessagingException {
        var user = userMapper.convertDtoToEntity(userDto);
        securityService.register(user, request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/register/verify", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyRegister(@RequestParam(name = "code", required = true)
                                            String verificationCode) {
        securityService.verifyRegister(verificationCode);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody LoginDto credentials) {
        var responseBuilder = ResponseEntity.ok();
        var authentication = securityService.authenticate(credentials);

        var tokens = jwtHelper.createRefreshAndAccessToken((UserDetails) authentication.getPrincipal());

        var cookies = CookieUtil.createJwtCookies(tokens, domain);

        responseBuilder = credentials.rememberMe() ?
                responseBuilder.header(HttpHeaders.SET_COOKIE, cookies[0].toString(), cookies[1].toString()) :
                responseBuilder.header(HttpHeaders.SET_COOKIE, cookies[0].toString());

        return responseBuilder.build();
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> validateToken(@CookieValue(name = "token", required = false) String token,
                                           @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        var user = securityService.authenticatedUser();
        var responseBuilder = ResponseEntity.ok();
        log.debug("validate refresh token - email - {}", user.getUsername());

        boolean validate = jwtHelper.validateToken(Optional.ofNullable(token), user);

        if (!validate) {
            var tokens = jwtHelper.createRefreshAndAccessToken(user);
            var cookies = CookieUtil.createJwtCookies(tokens, domain);
            responseBuilder = responseBuilder.header(
                    HttpHeaders.SET_COOKIE,
                    cookies[0].toString()
            );
        }
        return responseBuilder.body(user.getId());
    }

}
