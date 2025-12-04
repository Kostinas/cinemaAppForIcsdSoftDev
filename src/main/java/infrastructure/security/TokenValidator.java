package infrastructure.security;

import domain.entity.value.UserId;
import domain.enums.BaseRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class TokenValidator {

    private final String secretKey;

    public TokenValidator(String secretKey) {
        this.secretKey = secretKey;
    }

    public TokenData validate(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        Long userId = Long.valueOf(claims.getSubject());
        String role = claims.get("role", String.class);

        return new TokenData(new UserId(userId), BaseRole.valueOf(role));
    }


    public record TokenData(UserId userId, BaseRole role) {}
}
