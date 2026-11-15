package com.ait.delivery.delivery.api.infrastructure.persistence.adapter;

import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.OrderEntity;
import com.ait.delivery.delivery.api.infrastructure.persistence.mapper.OrderMapper;
import com.ait.delivery.delivery.api.infrastructure.persistence.repository.OrderJpaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository jpa;

    public OrderRepositoryAdapter(OrderJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.domainToEntity(order);
        return OrderMapper.entityToDomain(jpa.save(entity));
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpa.findById(id).map(OrderMapper::entityToDomain);
    }

    @Override
    public List<Order> findAll(OrderFilter filter) {
        Specification<OrderEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if (filter.origin() != null) {
                predicates.add(cb.like(cb.lower(root.get("origin")),
                        "%" + filter.origin().toLowerCase() + "%"));
            }

            if (filter.destination() != null) {
                predicates.add(cb.like(cb.lower(root.get("destination")),
                        "%" + filter.destination().toLowerCase() + "%"));
            }

            if (filter.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdFrom()));
            }

            if (filter.createdTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), filter.createdTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return jpa.findAll(spec).stream().map(OrderMapper::entityToDomain).toList();
    }
}
