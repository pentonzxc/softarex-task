package com.nikolai.softarex.util;

import ch.qos.logback.core.util.Duration;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseCookie;


@UtilityClass
public class CookieUtil {

    public ResponseCookie createJwtCookie(String token , String domain){
        return ResponseCookie.from("token" , token)
                .domain(domain)
                .path("/")
                .maxAge(Duration.buildByDays(3).getMilliseconds())
                .build();
    }

    public ResponseCookie createRefreshJwtCookie(String token, String domain){
        return ResponseCookie.from("refresh_token" , token)
                .domain(domain)
                .path("/")
                .maxAge(Duration.buildByDays(9).getMilliseconds())
                .build();
    }


}
