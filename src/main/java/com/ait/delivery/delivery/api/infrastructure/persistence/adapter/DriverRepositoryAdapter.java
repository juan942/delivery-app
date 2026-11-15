package com.ait.delivery.delivery.api.infrastructure.persistence.adapter;

import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.port.out.DriverRepository;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.DriverEntity;
import com.ait.delivery.delivery.api.infrastructure.persistence.mapper.DriverMapper;
import com.ait.delivery.delivery.api.infrastructure.persistence.repository.DriverJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DriverRepositoryAdapter implements DriverRepository {

    private final DriverJpaRepository jpa;

    public DriverRepositoryAdapter(DriverJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Driver save(Driver driver) {
        DriverEntity entity = DriverMapper.domainToEntity(driver);
        return DriverMapper.entityToDomain(jpa.save(entity));
    }

    @Override
    public Optional<Driver> findById(UUID id) {
        return jpa.findById(id).map(DriverMapper::entityToDomain);
    }

    @Override
    public List<Driver> findAllActive() {
        return jpa.findByActiveTrue().stream().map(DriverMapper::entityToDomain).toList();
    }

    @Override
    public List<Driver> findAll() {
        return jpa.findAll().stream().map(DriverMapper::entityToDomain).toList();
    }
}
