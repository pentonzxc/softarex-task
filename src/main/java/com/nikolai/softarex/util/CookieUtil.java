package com.nikolai.softarex.util;

import ch.qos.logback.core.util.Duration;
import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;


@UtilityClass
public class CookieUtil {

    public ResponseCookie createJwtCookie(String token, String domain) {
        return ResponseCookie.from("token", token)
                .domain(domain)
                .path("/")
                .maxAge(Duration.buildByDays(3).getMilliseconds())
                .build();
    }

    public ResponseCookie createRefreshJwtCookie(String token, String domain) {
        return ResponseCookie.from("refresh_token", token)
                .domain(domain)
                .path("/")
                .maxAge(Duration.buildByDays(9).getMilliseconds())
                .build();
    }

    public ResponseCookie[] createJwtCookies(String[] tokens, String domain) {
        var jwtCookie = CookieUtil.createJwtCookie(tokens[0], domain);
        var refreshJwtCookie = CookieUtil.createRefreshJwtCookie(tokens[1], domain);

        return new ResponseCookie[]{
                jwtCookie, refreshJwtCookie
        };
    }

    public Cookie createInvalidJwtServletCookie(String domain) {
        var token = new Cookie("token", "");
        token.setPath("/");
        token.setDomain(domain);
        token.setMaxAge(0);

        return token;
    }

    public Cookie createInvalidRefreshJwtServletCookie(String domain) {
        var token = new Cookie("refresh_token", "");
        token.setPath("/");
        token.setDomain(domain);
        token.setMaxAge(0);

        return token;
    }

}
