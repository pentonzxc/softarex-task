package com.nikolai.softarex.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(value = "failedAuthHandler")
@Slf4j
public class FailedAuthenticationHandler implements AuthenticationEntryPoint {

    @Value("${cookies.domain}")
    private String domain;


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

/*        if (request.getRequestURL().toString().equals("http://localhost:8080/error")) {
            return;
        }
        response.addCookie(CookieUtil.createInvalidJwtServletCookie(domain));
        response.addCookie(CookieUtil.createInvalidRefreshJwtServletCookie(domain));
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid tokens");*/

        log.debug("request error :: {}", authException.getMessage());
    }
}
