package com.ait.delivery.delivery.api.domain.exception;

import java.util.UUID;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(UUID id) {
        super("Order not found: " + id);
    }
}
