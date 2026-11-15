package com.ait.delivery.delivery.api.infrastructure.web.dto;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeOrderStatusRequestDto(@NotNull OrderStatus status) {
}
