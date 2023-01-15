package com.nikolai.softarex.filter;

import com.nikolai.softarex.model.User;
import com.nikolai.softarex.service.UserService;
import com.nikolai.softarex.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private UserService userService;

    private JwtService jwtService;


    @Autowired
    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Find jwt cookie");

        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<Cookie> jwtOpt = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwt".equals(cookie.getName()))
                .findAny();

        if (jwtOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        logger.debug("Retrieve user from token");

        String token = jwtOpt.get().getValue();
        UserDetails userDetails = null;
        try {
            Optional<User> userOpt = userService.findByEmail(jwtService.getUsernameFromToken(token));
            userDetails = userOpt.orElse(null);
        } catch (JwtException e) {
            filterChain.doFilter(request, response);
            return;
        }

        logger.debug("Validate token");

        if (!jwtService.validateToken(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, AuthorityUtils.NO_AUTHORITIES
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.debug("Set authentication in SecurityContextHolder");

        filterChain.doFilter(request, response);
    }
}
