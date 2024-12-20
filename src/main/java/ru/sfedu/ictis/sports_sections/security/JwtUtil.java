package ru.sfedu.ictis.sports_sections.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String key;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    public String generateToken(String email, Long id) {
        Date now = new Date();
        return "Bearer " + Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMillis))
                .claim("id", id)
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String extractEmail(String token) {
        return getJwtParser().parseClaimsJws(token).getBody().getSubject();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return getJwtParser().parseClaimsJws(token).getBody().getExpiration();
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getBytes())).build();
    }
}
