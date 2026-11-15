package com.ait.delivery.delivery.api.infrastructure.persistence.mapper;

import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.OrderEntity;

public class OrderMapper {
    private OrderMapper() {}

    public static OrderEntity domainToEntity(Order model) {
        return new OrderEntity(
                model.getId(),
                model.getStatus(),
                model.getOrigin(),
                model.getDestination(),
                model.getCreatedAt(),
                model.getUpdatedAt());
    }

    public static Order entityToDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getStatus(),
                entity.getOrigin(),
                entity.getDestination(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
