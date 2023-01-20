package com.nikolai.softarex.filter;

import com.nikolai.softarex.exception.InvalidTokenException;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final JwtService jwtService;

    private AuthenticationManager authenticationManager;

    private static final String COOKIES_MATCHER = "^(token)|(refresh_token)$";


    @Autowired
    public JwtFilter(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        log.debug("JWT Filter - {}", request.getRequestURL());

        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }


//        if(!request.getRequestURL().equals("http://localhost:8080/v1/api/auth/validate")){
//            filterChain.doFilter(request , response);
//            return;
//        }


        var cookies = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().matches(COOKIES_MATCHER))
                .sorted((Comparator.comparing(Cookie::getName)))
                .toArray(Cookie[]::new);

        if (cookies.length == 0) {
            filterChain.doFilter(request, response);
            return;
        }

        var firstToken = cookies[0];
        UserDetails userDetails = null;


        try {
            Optional<User> userOpt = userService.findByEmail(jwtService.getUsernameFromToken(firstToken.getValue()));
            userDetails = userOpt.orElse(null);
        } catch (JwtException e) {
            filterChain.doFilter(request, response);
            return;
        }

        boolean isFirstInvalid = !jwtService.validateToken(firstToken.getValue(), userDetails);

        log.debug("First token - {} : isInvalid - {}", firstToken.getName(), isFirstInvalid);

        if (cookies.length == 1 && isFirstInvalid) {
            throw new InvalidTokenException();
        } else if (cookies.length == 2 && isFirstInvalid) {
            var secondToken = cookies[1];
            boolean isSecondInvalid = !jwtService.validateToken(secondToken.getValue(), userDetails);

            log.debug("Second token - {} : isInvalid - {}", secondToken.getName(), isSecondInvalid);

            if (!isSecondInvalid) {
                throw new InvalidTokenException();
            }

        }

        var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, AuthorityUtils.NO_AUTHORITIES
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
