package com.nikolai.softarex.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
