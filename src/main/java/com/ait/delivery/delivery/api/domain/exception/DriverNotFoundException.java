package com.ait.delivery.delivery.api.domain.exception;

import java.util.UUID;

public class DriverNotFoundException extends DomainException {
    public DriverNotFoundException(UUID id) {
        super("Driver not found: " + id);
    }
}
