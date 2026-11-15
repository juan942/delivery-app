package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.exception.InvalidArgumentException;

import java.util.UUID;

public class Driver {
    private final UUID id;
    private final String name;
    private final String licenseNumber;
    private boolean active;

    public Driver(UUID id, String name, String licenseNumber, boolean active) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.active = active;
    }

    public static Driver create(String name, String licenseNumber) {
        validateText(name, "name");
        validateText(licenseNumber, "licenseNumber");
        return new Driver(UUID.randomUUID(), name, licenseNumber, true);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    private static void validateText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new InvalidArgumentException(field + " must not be blank");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public boolean isActive() {
        return active;
    }
}
