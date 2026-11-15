package com.ait.delivery.delivery.api.domain.port.in;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;

import java.util.List;
import java.util.UUID;

public interface OrderUseCases {
    Order create(String origin, String destination);
    Order changeStatus(UUID orderId, OrderStatus next);
    Order getById(UUID orderId);
    List<Order> list(OrderFilter filter);
}
