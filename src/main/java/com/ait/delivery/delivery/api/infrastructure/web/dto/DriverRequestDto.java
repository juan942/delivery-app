package com.ait.delivery.delivery.api.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DriverRequestDto(
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Size(max = 50) String licenseNumber
) {
}
