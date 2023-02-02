package com.nikolai.softarex.web.controller.exception;


import com.nikolai.softarex.security.util.CookieUtil;
import com.nikolai.softarex.web.exception.UserAlreadyExistException;
import com.nikolai.softarex.web.exception.UserNotVerifyException;
import com.nikolai.softarex.web.exception.VerificationCodeNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.security.sasl.AuthenticationException;

@RestControllerAdvice
public class AuthExceptionController extends ResponseEntityExceptionHandler {


    @Value("${cookies.domain}")
    private String domain;

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<?> handleJwtException(JwtException ex) {
        var cookies = CookieUtil.createInvalidJwtCookies(domain);
        var responseBuilder = ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        responseBuilder.header(HttpHeaders.SET_COOKIE, cookies[0].toString(), cookies[1].toString());

        return ex instanceof ExpiredJwtException ?
                responseBuilder.body("Tokens are expired") :
                responseBuilder.body("Tokens are invalid");
    }


    @ExceptionHandler(VerificationCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleVerificationCodeException(VerificationCodeNotFoundException ex) {
        return new ResponseEntity<>("Verification code doesn't exist", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotVerifyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserNotVerifyException(UserNotVerifyException ex) {
        return new ResponseEntity<>("User doesn't confirmed verification", HttpStatus.BAD_REQUEST);
    }


}
