package com.ait.delivery.delivery.api.infrastructure.web.dto;

import com.ait.delivery.delivery.api.domain.model.Driver;

import java.util.UUID;

public record DriverResponseDto(
        UUID id,
        String name,
        String licenseNumber,
        boolean active
) {
    public static DriverResponseDto convert(Driver domain) {
        return new DriverResponseDto(
                domain.getId(),
                domain.getName(),
                domain.getLicenseNumber(),
                domain.isActive()
        );
    }
}
