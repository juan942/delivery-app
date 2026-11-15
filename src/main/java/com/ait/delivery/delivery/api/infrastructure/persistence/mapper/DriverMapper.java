package com.ait.delivery.delivery.api.infrastructure.persistence.mapper;

import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.DriverEntity;

public class DriverMapper {
    private DriverMapper() {}

    public static DriverEntity domainToEntity(Driver model) {
        return new DriverEntity(
                model.getId(),
                model.getName(),
                model.getLicenseNumber(),
                model.isActive()
        );
    }

    public static Driver entityToDomain(DriverEntity entity) {
        return new Driver(
                entity.getId(),
                entity.getName(),
                entity.getLicenseNumber(),
                entity.isActive()
        );
    }
}
