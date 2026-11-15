package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.OrderNotFoundException;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;
import com.ait.delivery.delivery.api.domain.port.in.OrderUseCases;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements OrderUseCases {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(String origin, String destination) {
        Order newOrder = Order.create(origin, destination);
        return orderRepository.save(newOrder);
    }

    @Override
    public Order changeStatus(UUID orderId, OrderStatus next) {
        Order order = getById(orderId);
        order.transitionTo(next);
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> list(OrderFilter filter) {
        return orderRepository.findAll(filter);
    }
}
