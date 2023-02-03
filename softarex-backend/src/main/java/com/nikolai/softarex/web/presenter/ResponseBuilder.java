package com.nikolai.softarex.web.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseBuilder<T> {
    ResponseEntity<T> response(HttpStatus status, T body);

}
