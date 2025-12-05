package com.cinema.presentation.dto.responses;

public record AuthResponse(String token, Long userId, String role) {
}
