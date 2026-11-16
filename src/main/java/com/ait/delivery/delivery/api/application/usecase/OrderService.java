package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.OrderNotFoundException;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;
import com.ait.delivery.delivery.api.domain.port.in.OrderUseCases;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService implements OrderUseCases {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order create(String origin, String destination) {
        Order newOrder = Order.create(origin, destination);
        Order saved = orderRepository.save(newOrder);
        log.info("Order created: id={}, origin={}, destination={}", saved.getId(), saved.getOrigin(), saved.getDestination());
        return saved;
    }

    @Override
    public Order changeStatus(UUID orderId, OrderStatus next) {
        Order order = getById(orderId);
        OrderStatus previous = order.getStatus();
        order.transitionTo(next);
        Order saved = orderRepository.save(order);
        log.info("Order status changed: id={}, from={}, to={}", orderId, previous, next);
        return saved;
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
