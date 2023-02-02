package com.nikolai.softarex.web.presenter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

public class CookieResponse<T> implements ResponseBuilder<T> {
    private ResponseBuilder<T> response;

    private final ResponseCookie[] cookies;

    public CookieResponse(ResponseBuilder<T> response, ResponseCookie[] cookies) {
        this.response = response;
        this.cookies = cookies;
    }

    @Override
    public ResponseEntity<T> response(HttpStatus status, T body) {
        return ResponseEntity.ok().headers(mapCookie()).body(body);
    }

    private HttpHeaders mapCookie() {
        HttpHeaders cookieHeader = new HttpHeaders();
        Arrays.stream(cookies).forEach((cookie) -> cookieHeader.add(HttpHeaders.SET_COOKIE, cookie.toString()));

        return cookieHeader;
    }

}
