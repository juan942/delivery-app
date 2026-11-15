package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.exception.InvalidArgumentException;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Assignment {
    private final UUID id;
    private final UUID orderId;
    private final UUID driverId;
    private final ZonedDateTime createdAt;
    private ZonedDateTime endedAt;
    private final String pdfPath;
    private final String imagePath;

    private Assignment(UUID id, UUID orderId, UUID driverId, ZonedDateTime createdAt,
                       ZonedDateTime endedAt, String pdfPath, String imagePath) {
        this.id = id;
        this.orderId = orderId;
        this.driverId = driverId;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.pdfPath = pdfPath;
        this.imagePath = imagePath;
    }

    public static Assignment create(UUID orderId, UUID driverId, String pdfPath, String imagePath) {
        validateField(orderId, "orderId");
        validateField(driverId, "driverId");
        return new Assignment(UUID.randomUUID(), orderId, driverId, ZonedDateTime.now(),
                null, pdfPath, imagePath);
    }

    public void close() {
        if (this.endedAt == null) {
            this.endedAt = ZonedDateTime.now();
        }
    }

    public boolean isActive() {
        return endedAt == null;
    }

    private static void validateField(UUID value, String field) {
        if (value == null) {
            throw new InvalidArgumentException(field + " must not be null");
        }
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getEndedAt() {
        return endedAt;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
