package com.ait.delivery.delivery.api.infrastructure.persistence.mapper;

import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.infrastructure.persistence.entity.AssignmentEntity;

public class AssignmentMapper {
    private AssignmentMapper() {}

    public static AssignmentEntity domainToEntity(Assignment model) {
        return new AssignmentEntity(
                model.getId(),
                model.getOrderId(),
                model.getDriverId(),
                model.getCreatedAt(),
                model.getEndedAt(),
                model.getPdfPath(),
                model.getImagePath()
        );
    }

    public static Assignment entityToDomain(AssignmentEntity entity) {
        return new Assignment(
                entity.getId(),
                entity.getOrderId(),
                entity.getDriverId(),
                entity.getCreatedAt(),
                entity.getEndedAt(),
                entity.getPdfPath(),
                entity.getImagePath()
        );
    }
}
