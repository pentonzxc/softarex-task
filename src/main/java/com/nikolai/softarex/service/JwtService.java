package com.nikolai.softarex.service;

import com.nikolai.softarex.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Date;
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

    private UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }


    public String createToken(UserDetails user) {
        Date exp = new Date(expireTime * 1000 + System.currentTimeMillis());
        return doToken(user.getUsername(), exp);
    }

    public String createRefreshToken(UserDetails user) {
        Date exp = new Date(refreshExpireTime * 1000 + System.currentTimeMillis());
        return doToken(user.getUsername(), exp);
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
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        String username = getUsernameFromToken(token);
        return (userDetails != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    private Claims getAllClaimsFromToken(String token) {
        return (Claims) Jwts.parser().setSigningKey(secretKey).parse(token).getBody();
    }


}
