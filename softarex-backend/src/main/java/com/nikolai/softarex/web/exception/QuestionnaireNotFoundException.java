package com.nikolai.softarex.web.exception;

public class QuestionnaireNotFoundException extends RuntimeException {
    public QuestionnaireNotFoundException() {
        super();
    }

    public QuestionnaireNotFoundException(String message) {
        super(message);
    }
}
