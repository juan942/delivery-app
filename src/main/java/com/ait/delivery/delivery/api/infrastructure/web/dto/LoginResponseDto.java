package com.ait.delivery.delivery.api.infrastructure.web.dto;

public record LoginResponseDto(
        String token,
        String tokenType,
        long expiresIn
) {
}
