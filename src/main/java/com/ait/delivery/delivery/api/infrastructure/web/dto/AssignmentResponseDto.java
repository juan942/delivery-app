package com.ait.delivery.delivery.api.infrastructure.web.dto;

import com.ait.delivery.delivery.api.domain.model.Assignment;

import java.time.ZonedDateTime;
import java.util.UUID;

public record AssignmentResponseDto(
        UUID id,
        UUID orderId,
        UUID driverId,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        String pdfPath,
        String imagePath,
        boolean active
) {
    public static AssignmentResponseDto convert(Assignment domain) {
        return new AssignmentResponseDto(
                domain.getId(),
                domain.getOrderId(),
                domain.getDriverId(),
                domain.getCreatedAt(),
                domain.getEndedAt(),
                domain.getPdfPath(),
                domain.getImagePath(),
                domain.isActive()
        );
    }
}
