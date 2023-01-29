package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.LoginDto;
import com.nikolai.softarex.dto.UserDto;
import com.nikolai.softarex.entity.User;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.mapper.EntityMapper;
import com.nikolai.softarex.presenter.CookieResponse;
import com.nikolai.softarex.presenter.ResponseBuilder;
import com.nikolai.softarex.service.SecurityService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@Slf4j
public class AuthController {

    private final EntityMapper<User, UserDto> userMapper;

    private final SecurityService securityService;

    private final ResponseBuilder<Object> responseBuilder;

    @Value("${cookies.domain}")
    private String domain;

    @Autowired
    public AuthController(@Qualifier("userMapper") EntityMapper<User, UserDto> userMapper,
                          SecurityService securityService,
                          @Qualifier("contentResponse") ResponseBuilder<Object> responseBuilder) {
        this.userMapper = userMapper;
        this.securityService = securityService;
        this.responseBuilder = responseBuilder;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody UserDto userDto,
                                      HttpServletRequest request
    ) throws UserAlreadyExistException, MessagingException {
        var user = userMapper.convertDtoToEntity(userDto);
        securityService.register(user, request);

        return responseBuilder.response(HttpStatus.CREATED, null);
    }


    @RequestMapping(value = "/register/verify", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyRegister(@RequestParam(name = "code", required = true)
                                            String verificationCode) {
        securityService.verifyRegister(verificationCode);

        return responseBuilder.response(HttpStatus.OK, null);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody LoginDto credentials) {
        var jwtCookies = securityService.login(credentials);

        return new CookieResponse<>(responseBuilder, jwtCookies).response(HttpStatus.OK, null);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> verifyAccessToken(@CookieValue(name = "token", required = false) String accessToken,
                                               @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        var user = securityService.authenticatedUser();
        var updatedToken = securityService.verifyIfRequireUpdateToken(user, accessToken, refreshToken);

        return new CookieResponse<>(responseBuilder, new ResponseCookie[]{
                updatedToken
        }).response(HttpStatus.OK, user.getId());
    }

}
