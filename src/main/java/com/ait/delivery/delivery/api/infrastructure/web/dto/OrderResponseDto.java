package com.ait.delivery.delivery.api.infrastructure.web.dto;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.model.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderResponseDto(
        UUID id,
        OrderStatus status,
        String origin,
        String destination,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderResponseDto convert(Order domain) {
        return new OrderResponseDto(
                domain.getId(),
                domain.getStatus(),
                domain.getOrigin(),
                domain.getDestination(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}
