package application.users;

import infrastructure.security.TokenService;

public class LogoutUseCase {
    private final TokenService tokenService;

    public LogoutUseCase(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void logout(String token) {
        if (token == null || token.isBlank())
            throw new IllegalArgumentException("Token cannot be null or empty");

        tokenService.invalidate(token);
    }
}
