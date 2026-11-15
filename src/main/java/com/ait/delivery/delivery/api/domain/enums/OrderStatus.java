package com.ait.delivery.delivery.api.domain.enums;

import com.ait.delivery.delivery.api.domain.exception.InvalidOrderStateTransitionException;

import java.util.Set;

public enum OrderStatus {
    CREATED,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED;

    public boolean canTransitionTo(OrderStatus next) {
        return allowedTransitions().contains(next);
    }

    public OrderStatus transitionTo(OrderStatus next) {
        if (!canTransitionTo(next)) {
            throw new InvalidOrderStateTransitionException(this, next);
        }
        return next;
    }

    public Set<OrderStatus> allowedTransitions() {
        return switch (this) {
            case CREATED -> Set.of(IN_TRANSIT, CANCELLED);
            case IN_TRANSIT -> Set.of(DELIVERED);
            default -> Set.of();
        };
    }

}
