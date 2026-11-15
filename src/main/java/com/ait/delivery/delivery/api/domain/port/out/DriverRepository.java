package com.ait.delivery.delivery.api.domain.port.out;

import com.ait.delivery.delivery.api.domain.model.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository {
    Driver save(Driver save);
    Optional<Driver> findById(UUID id);
    List<Driver> findAllActive();
    List<Driver> findAll();
}
