package com.ait.delivery.delivery.api.domain.port.out;

import com.ait.delivery.delivery.api.domain.model.Assignment;

import java.util.List;
import java.util.UUID;

public interface AssignmentRepository {
    Assignment save(Assignment assignment);
    boolean existsActiveByOrderId(UUID orderId);
    List<Assignment> finByOrderIdAndDriverId();
    List<Assignment> findAll();
}
