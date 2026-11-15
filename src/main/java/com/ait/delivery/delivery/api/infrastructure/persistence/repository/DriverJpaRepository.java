package com.ait.delivery.delivery.api.infrastructure.persistence.repository;

import com.ait.delivery.delivery.api.infrastructure.persistence.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DriverJpaRepository extends JpaRepository<DriverEntity, UUID> {
    List<DriverEntity> findByActiveTrue();
}
