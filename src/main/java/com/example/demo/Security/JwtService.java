package com.example.demo.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

    private final Key key;	// the key to verify with (token)
    private final long expMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.exp.minutes:60}") long expMinutes) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expMinutes = expMinutes;
    }

    // generate token
    public String generate(String username, Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)               // e.g: roles
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expMinutes * 60)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // parse token
    /*
    If the token is valid:

    	It decodes the token into a Jws<Claims> object.

    	If the token is invalid, expired, or tampered with:

    	It throws an exception (JwtException or ExpiredJwtException).
    */
    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    // username(token) → extracts the subject (username/email) from the decoded token.
    public String username(String token) { return parse(token).getBody().getSubject(); }
}
