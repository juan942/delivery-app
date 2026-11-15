package com.ait.delivery.delivery.api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderRequestDto(
        @NotBlank @Size(max = 250) String origin,
        @NotBlank @Size(max = 250) String destination
) {
}
