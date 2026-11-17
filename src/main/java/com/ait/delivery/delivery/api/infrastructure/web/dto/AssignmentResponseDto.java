package com.ait.delivery.delivery.api.infrastructure.web.dto;

import com.ait.delivery.delivery.api.domain.model.Assignment;

import java.time.LocalDateTime;
import java.util.UUID;

public record AssignmentResponseDto(
        UUID id,
        UUID orderId,
        UUID driverId,
        LocalDateTime createdAt,
        LocalDateTime endedAt,
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
