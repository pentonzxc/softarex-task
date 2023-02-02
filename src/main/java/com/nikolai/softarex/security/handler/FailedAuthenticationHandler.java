package com.nikolai.softarex.security.handler;

import com.nikolai.softarex.security.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component(value = "failedAuthHandler")
public class FailedAuthenticationHandler implements AuthenticationEntryPoint {

    @Value("${cookies.domain}")
    private String domain;


    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.addCookie(CookieUtil.createInvalidJwtServletCookie(domain));
        response.addCookie(CookieUtil.createInvalidRefreshJwtServletCookie(domain));

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid tokens");
    }
}
