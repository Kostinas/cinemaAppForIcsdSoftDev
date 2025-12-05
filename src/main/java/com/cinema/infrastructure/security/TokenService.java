package com.cinema.infrastructure.security;

import com.cinema.domain.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class TokenService {
    private final String secretKey;
    private final Long expirationSeconds;

    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public TokenService(String secretKey , Long expirationSeconds){
        this.secretKey = secretKey;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();


        return Jwts.builder()
                .setSubject(String.valueOf(user.id().value()))
                .claim("username", user.username().value())
                .claim("role", user.baseRole().name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void invalidate(String token) {
        if (token != null && !token.isBlank()) {
            blacklistedTokens.add(token);
        }
    }
    public boolean isInvalidated(String token) {
        return blacklistedTokens.contains(token);
    }

    public void clearBlacklist() {
        blacklistedTokens.clear();
    }

}
