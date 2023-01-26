package com.nikolai.softarex.controllers.exception;

import com.nikolai.softarex.exception.QuestionnaireNotFoundException;
import com.nikolai.softarex.exception.UserAlreadyExistException;
import com.nikolai.softarex.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class QuestionnaireExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QuestionnaireNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleUserNotFoundException(QuestionnaireNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
