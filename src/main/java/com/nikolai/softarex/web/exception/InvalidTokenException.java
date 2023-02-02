package com.nikolai.softarex.web.exception;


import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(){
        super("Invalid token");
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
