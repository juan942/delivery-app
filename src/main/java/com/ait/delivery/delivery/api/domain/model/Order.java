package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.InvalidArgumentException;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Order {
    private final UUID id;
    private OrderStatus status;
    private final String origin;
    private final String destination;
    private final ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public Order(UUID id, OrderStatus status, String origin, String destination, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.status = status;
        this.origin = origin;
        this.destination = destination;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order create(String origin, String destination) {
        validateText(origin, "origin");
        validateText(destination, "destination");
        ZonedDateTime now = ZonedDateTime.now();
        return new Order(UUID.randomUUID(), OrderStatus.CREATED, origin, destination, now, now);
    }

    public void transitionTo(OrderStatus next) {
        this.status = this.status.transitionTo(next);
        this.updatedAt = ZonedDateTime.now();
    }

    private static void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidArgumentException(field + " must not be blank");
        }
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
