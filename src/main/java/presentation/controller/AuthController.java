package presentation.controller;

import application.users.AuthenticateUserUseCase;
import application.users.LogoutUseCase;
import application.users.ValidateTokenUseCase;
import application.users.ValidateTokenUseCase.TokenData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import presentation.dto.requests.LoginRequest;
import presentation.dto.responses.AuthResponse;
import presentation.dto.responses.TokenInfoResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUser;
    private final LogoutUseCase logoutUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;

    public AuthController(AuthenticateUserUseCase authenticateUser,
                          LogoutUseCase logoutUseCase,
                          ValidateTokenUseCase validateTokenUseCase) {
        this.authenticateUser = authenticateUser;
        this.logoutUseCase = logoutUseCase;
        this.validateTokenUseCase = validateTokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        String token = authenticateUser.authenticate(
                request.username(),
                request.password()
        );


        AuthResponse response = new AuthResponse(token, null, null);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(name = "Authorization", required = false) String authHeader
    ) {
        String token = extractBearerToken(authHeader);
        logoutUseCase.logout(token);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/validate")
    public ResponseEntity<TokenInfoResponse> validate(
            @RequestHeader(name = "Authorization", required = false) String authHeader
    ) {
        String token = extractBearerToken(authHeader);
        TokenData data = validateTokenUseCase.validate(token);
        TokenInfoResponse response = new TokenInfoResponse(data.userId(), data.role());
        return ResponseEntity.ok(response);
    }

    private String extractBearerToken(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Missing or invalid Authorization header"
            );
        }
        return header.substring(7);
    }
}
