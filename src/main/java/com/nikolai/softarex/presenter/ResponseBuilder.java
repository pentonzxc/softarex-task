package com.nikolai.softarex.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseBuilder<T> {
    ResponseEntity<T> response(HttpStatus status, T body);

}
