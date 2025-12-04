package infrastructure.security;

import domain.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.time.Instant;
import java.util.Date;

public class TokenService {
    private final String secretKey;
    private final Long expirationSeconds;

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



}
