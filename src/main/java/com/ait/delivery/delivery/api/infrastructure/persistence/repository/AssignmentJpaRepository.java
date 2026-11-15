package com.ait.delivery.delivery.api.infrastructure.persistence.repository;

import com.ait.delivery.delivery.api.infrastructure.persistence.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssignmentJpaRepository extends JpaRepository<AssignmentEntity, UUID> {
}
