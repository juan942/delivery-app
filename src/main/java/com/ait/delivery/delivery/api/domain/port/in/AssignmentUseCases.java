package com.ait.delivery.delivery.api.domain.port.in;

import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.model.FileUpload;

import java.util.List;
import java.util.UUID;

public interface AssignmentUseCases {
    Assignment assign(UUID orderId, UUID driverId, FileUpload pdf, FileUpload image);
    List<Assignment> list(UUID orderId, UUID driverId);
}
