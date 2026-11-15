package com.ait.delivery.delivery.api.domain.exception;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;

public class InvalidOrderStateTransitionException extends DomainException {
    private final OrderStatus from;
    private final OrderStatus to;

    public InvalidOrderStateTransitionException(OrderStatus from, OrderStatus to) {
        super("Cannot transition from " + from + " to " + to);
        this.from = from;
        this.to = to;
    }

    public OrderStatus getFrom() {
        return from;
    }

    public OrderStatus getTo() {
        return to;
    }
}
