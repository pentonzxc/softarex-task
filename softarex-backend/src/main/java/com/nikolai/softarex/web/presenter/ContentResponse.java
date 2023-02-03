package com.nikolai.softarex.web.presenter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("contentResponse")
public class ContentResponse<T> implements ResponseBuilder<T> {
    @Override
    public ResponseEntity<T> response(HttpStatus status, T body) {
        return ResponseEntity.status(status).body(body);
    }
}
