package application.users;

import domain.Exceptions.AuthorizationException;
import domain.Exceptions.NotFoundException;
import domain.Exceptions.ValidationException;
import domain.entity.User;
import domain.entity.value.Username;
import domain.port.UserRepository;
import infrastructure.security.TokenService;

public final class AuthenticateUserUseCase {


    private final UserRepository userRepository;
    private final TokenService tokenService;

    public AuthenticateUserUseCase(UserRepository userRepository,
                                   TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }


    public String authenticate(String rawUsername, String rawPassword) {
        if (rawUsername == null || rawUsername.isBlank() ||
                rawPassword == null || rawPassword.isBlank()) {
            throw new AuthorizationException("Invalid credentials");
        }

        Username username = Username.of(rawUsername);

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new AuthorizationException("Invalid username or password"));

        if (!user.isActive() || user.isLocked()) {
            throw new AuthorizationException("User is inactive or locked");
        }

        // Έλεγχος password (HashedPassword έχει μέθοδο matches)
        if (!user.password().matches(rawPassword)) {
            user.registerFailedLogin();
            userRepository.Save(user);
            throw new AuthorizationException("Invalid username or password");
        }

        // επιτυχία → μηδενίζουμε failedAttempts
        user.resetFailedAttempts();
        userRepository.Save(user);

        // δημιουργούμε JWT
        return tokenService.generateToken(user);
    }

}
