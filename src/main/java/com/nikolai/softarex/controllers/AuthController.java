package com.nikolai.softarex.controllers;

import com.nikolai.softarex.dto.LoginCredentials;
import com.nikolai.softarex.dto.RegisterCredentials;
import com.nikolai.softarex.exception.InvalidTokenException;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.mapper.CredentialsMapper;
import com.nikolai.softarex.service.AuthService;
import com.nikolai.softarex.service.JwtService;
import com.nikolai.softarex.service.UserService;
import com.nikolai.softarex.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
public class AuthController {
    private final JwtService jwtService;

    private final UserService userService;

    private final CredentialsMapper credentialsMapper;

    private AuthService authService;

    @Value("${cookies.domain}")
    private String domain;

    @Autowired
    public AuthController(JwtService jwtService, UserService userService, CredentialsMapper credentialsMapper, AuthService authService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.credentialsMapper = credentialsMapper;
        this.authService = authService;

    }


    @RequestMapping(value = "/register", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody
                                      RegisterCredentials credentials) throws UserAlreadyExistException {
        var user = credentialsMapper.registerCredentialsToUser(credentials);
        authService.register(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody
                                   LoginCredentials credentials) {
        var authentication = authService.authenticate(credentials);

        var response = tokensCreatureTemplate((UserDetails) authentication.getPrincipal());

        return response;
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@CookieValue(name = "token") String token,
                                           @AuthenticationPrincipal User user) {
        try {
            Boolean isValidToken = jwtService.validateToken(token, user);

            return ResponseEntity.ok(isValidToken);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.ok(false);
        }
    }

    @PutMapping("/renew")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token") String token,
                                          @AuthenticationPrincipal User user) {
        try {
            Boolean isValidToken = jwtService.validateToken(token, user);
            if (!isValidToken) {
                throw new InvalidTokenException();
            }
            var response = tokensCreatureTemplate(user);

            return response;
        } catch (ExpiredJwtException | InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    private ResponseEntity<?> tokensCreatureTemplate(UserDetails user) {
        var tokens = authService.createTokens(user);

        var jwtCookie = CookieUtil.createJwtCookie(tokens[0], domain);
        var refreshJwtCookie = CookieUtil.createRefreshJwtCookie(tokens[1], domain);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString(), refreshJwtCookie.toString())
                .build();
    }


}
