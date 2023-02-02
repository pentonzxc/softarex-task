package com.nikolai.softarex.web.controller;

import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.security.service.SecurityService;
import com.nikolai.softarex.web.dto.LoginDto;
import com.nikolai.softarex.web.dto.UserDto;
import com.nikolai.softarex.web.exception.UserAlreadyExistException;
import com.nikolai.softarex.web.mapper.EntityMapper;
import com.nikolai.softarex.web.presenter.CookieResponse;
import com.nikolai.softarex.web.presenter.ResponseBuilder;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@Slf4j
public class AuthController {

    private final EntityMapper<User, UserDto> userMapper;

    private final SecurityService securityService;

    private final ResponseBuilder<Object> responseBuilder;


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

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "http://localhost:3000/login")
                .build();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody LoginDto credentials) {
        var jwtCookies = securityService.login(credentials);

        return new CookieResponse<>(responseBuilder, jwtCookies).response(HttpStatus.OK, null);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ResponseEntity<?> verifyAccessToken(@CookieValue(name = "token", required = false) String accessToken,
                                               @CookieValue(name = "refresh_token", required = false) String refreshToken) {
        try {
            var user = securityService.authenticatedUser();
            var updatedTokenOpt = securityService.verifyIfRequireUpdateToken(user, accessToken, refreshToken);

            if (updatedTokenOpt.isPresent()) {
                return new CookieResponse<>(responseBuilder, new ResponseCookie[]{
                        updatedTokenOpt.get()
                }).response(HttpStatus.OK, user.getId());
            }
            return responseBuilder.response(HttpStatus.OK, Pair.of(user.getId(), user.getFullName()));

        } catch (JwtException exception) {
            return responseBuilder.response(HttpStatus.UNAUTHORIZED, null);
        }
    }

}
