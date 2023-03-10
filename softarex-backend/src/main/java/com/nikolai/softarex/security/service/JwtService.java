package com.nikolai.softarex.security.service;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expire-time}")
    private long expireTime;

    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    public String createToken(UserDetails user) {
        Date exp = new Date(expireTime * 1000 + System.currentTimeMillis());
        return this.doToken(user.getUsername(), exp);
    }

    public String createRefreshToken(UserDetails user) {
        Date exp = new Date(refreshExpireTime * 1000 + System.currentTimeMillis());
        return this.doToken(user.getUsername(), exp);
    }


    private String doToken(String username, Date expirationTime) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    /*public Date getIssuedAtDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getIssuedAt);
    }*/

    private boolean isTokenExpired(String token) {
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }


    public boolean validateToken(String token, String username) throws JwtException {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        var usernameFromToken = this.getUsernameFromToken(token);

        return usernameFromToken.equals(username) && !this.isTokenExpired(token);
    }

    public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        var usernameFromToken = this.getUsernameFromToken(token);

        return userDetails != null && usernameFromToken.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
    }


    private Claims getAllClaimsFromToken(String token) {
        return (Claims) Jwts.parser().setSigningKey(secretKey).parse(token).getBody();
    }

    public class JwtServiceHelper {
        public String[] createRefreshAndAccessToken(UserDetails userDetails) {
            String token = createToken(userDetails);
            String refreshToken = createRefreshToken(userDetails);
            return new String[]{
                    token, refreshToken
            };
        }


        public boolean validateToken(Optional<String> token, UserDetails user) {
            return token.filter(s -> JwtService.this.validateToken(s, user)).isPresent();
        }


        /*public boolean updateRequired(String token, UserDetails user) {
            try {
                return !JwtService.this.validateToken(token, user);
            } catch (ExpiredJwtException ex) {
                return false;
            }
        }*/
    }
}
