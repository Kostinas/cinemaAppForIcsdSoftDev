package presentation.dto.requests;

public record ChangePasswordRequest(String oldPassword, String newPassword) {
}
