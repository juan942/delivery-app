package com.ait.delivery.delivery.api.infrastructure.persistence.adapter;

import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.port.out.AssignmentRepository;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.AssignmentEntity;
import com.ait.delivery.delivery.api.infrastructure.persistence.mapper.AssignmentMapper;
import com.ait.delivery.delivery.api.infrastructure.persistence.repository.AssignmentJpaRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AssignmentRepositoryAdapter implements AssignmentRepository {

    private final AssignmentJpaRepository jpa;

    public AssignmentRepositoryAdapter(AssignmentJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Assignment save(Assignment assignment) {
        AssignmentEntity entity = AssignmentMapper.domainToEntity(assignment);
        return AssignmentMapper.entityToDomain(jpa.saveAndFlush(entity));
    }

    @Override
    public List<Assignment> findAll(UUID orderId, UUID driverId) {
        Specification<AssignmentEntity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (orderId != null) {
                predicates.add(cb.equal(root.get("orderId"), orderId));
            }
            if (driverId != null) {
                predicates.add(cb.equal(root.get("driverId"), driverId));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return jpa.findAll(spec).stream().map(AssignmentMapper::entityToDomain).toList();
    }

    @Override
    public Optional<Assignment> findActiveByOrderId(UUID orderId) {
        return jpa.findByOrderIdAndEndedAtIsNull(orderId).map(AssignmentMapper::entityToDomain);
    }

}
