package com.example.user_management_service.service;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.model.enums.UserRoleType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Monitored
public class JwtService {

    public final String SECRET;

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 30; // 30 minutes
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

    private final Map<String, String> refreshTokenStorage = new ConcurrentHashMap<>();

    public JwtService(@Value("${jwt.secret}") String secret) {
        if(secret == null || secret.isEmpty()){
            throw new IllegalArgumentException("JWT secret not set or null");
        }
        this.SECRET = secret;
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(Long id, String username, UserRoleType role) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id, username, role, ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(Long id, String username, UserRoleType role) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, id, username, role, REFRESH_TOKEN_EXPIRATION);
    }

    private String createToken(Map<String, Object> claims, Long id, String username, UserRoleType role, long expirationTime) {
        claims.put("username", username);
        claims.put("role", role.toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(id.toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void saveRefreshToken(String username, String refreshToken) {
        refreshTokenStorage.put(username, refreshToken);
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            validateToken(refreshToken);
            return refreshTokenStorage.containsValue(refreshToken);
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from refresh token
    public String getUsernameFromToken(String token) {
        System.out.println("Decoding token with secret: " + SECRET);
        var claims = Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }
}