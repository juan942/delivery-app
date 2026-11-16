package com.ait.delivery.delivery.api.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStatusTest {

    @Test
    void validTransitions() {
        assertTrue(OrderStatus.CREATED.canTransitionTo(OrderStatus.IN_TRANSIT));
        assertTrue(OrderStatus.CREATED.canTransitionTo(OrderStatus.CANCELLED));
        assertTrue(OrderStatus.IN_TRANSIT.canTransitionTo(OrderStatus.DELIVERED));
    }
    @Test
    void invalidTransitions() {
        assertFalse(OrderStatus.CREATED.canTransitionTo(OrderStatus.DELIVERED));
        assertFalse(OrderStatus.IN_TRANSIT.canTransitionTo(OrderStatus.CANCELLED));
        assertFalse(OrderStatus.DELIVERED.canTransitionTo(OrderStatus.CREATED));
        assertFalse(OrderStatus.CANCELLED.canTransitionTo(OrderStatus.IN_TRANSIT));
    }
}
