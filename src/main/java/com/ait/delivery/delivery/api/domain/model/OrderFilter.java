package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderFilter(OrderStatus status, String origin, String destination, LocalDateTime createdFrom, LocalDateTime createdTo) {
}
