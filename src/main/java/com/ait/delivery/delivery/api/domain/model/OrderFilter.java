package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;

import java.time.ZonedDateTime;

public record OrderFilter(OrderStatus status, String origin, String destination, ZonedDateTime createdFrom, ZonedDateTime createdTo) {
}
