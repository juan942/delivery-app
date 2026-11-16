package com.ait.delivery.delivery.api.domain.model;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.InvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void newOrderCorrectCreated() {
        Order order = Order.create("CDMX", "Queretaro");

        assertNotNull(order.getId());
        assertEquals(OrderStatus.CREATED, order.getStatus());
        assertEquals("CDMX", order.getOrigin());
        assertEquals("Queretaro", order.getDestination());
    }

    @Test
    void orderWithoutOriginFailed() {
        assertThrows(InvalidArgumentException.class, () -> Order.create("", "Queretaro"));
    }

    @Test
    void orderWithoutDestinationFailed() {
        assertThrows(InvalidArgumentException.class, () -> Order.create("CDMX", ""));
    }

    @Test
    void changeStatus() {
        Order order = Order.create("CDMX", "Queretaro");
        order.transitionTo(OrderStatus.IN_TRANSIT);
        assertEquals(OrderStatus.IN_TRANSIT, order.getStatus());
    }
}
