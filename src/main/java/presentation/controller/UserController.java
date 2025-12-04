package presentation.controller;

import application.users.*;
import domain.entity.value.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import presentation.dto.requests.RegisterUserRequest;
import presentation.dto.requests.ChangePasswordRequest;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUser;
    private final ChangePasswordUseCase changePassword;
    private final DeactivateUserUseCase deactivateUser;
    private final DeleteUserUseCase deleteUser;

    public UserController(
            RegisterUserUseCase registerUser,
            ChangePasswordUseCase changePassword,
            DeactivateUserUseCase deactivateUser,
            DeleteUserUseCase deleteUser
    ) {
        this.registerUser = registerUser;
        this.changePassword = changePassword;
        this.deactivateUser = deactivateUser;
        this.deleteUser = deleteUser;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequest request) {
        registerUser.register(
                request.username(),
                request.password(),
                request.fullName()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {

        changePassword.changePassword(
                new UserId(id),
                request.oldPassword(),
                request.newPassword()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deactivateUser.deactivate(new UserId(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteUser.delete(new UserId(id));
        return ResponseEntity.noContent().build();
    }
}
