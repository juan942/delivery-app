package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.OrderNotFoundException;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
        when(orderRepository.save(any(Order.class))).then(call -> call.getArgument(0));
        Order result = orderService.create("CDMX", "Puebla");

        assertEquals(OrderStatus.CREATED, result.getStatus());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void changeStatusOrder() {
        Order order = Order.create("CDMX", "Puebla");
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.changeStatus(order.getId(), OrderStatus.IN_TRANSIT);

        assertEquals(OrderStatus.IN_TRANSIT, result.getStatus());
    }

    @Test
    void getByIdFailedWhenOrderNotExist() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getById(id));
    }
}
