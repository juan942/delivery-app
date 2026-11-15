package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;
import com.ait.delivery.delivery.api.domain.port.in.OrderUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ChangeOrderStatusRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.OrderRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.OrderResponseDto;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderUseCases useCases;

    public OrderController(OrderUseCases useCases) {
        this.useCases = useCases;
    }

    @PostMapping
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto request) {
        Order response = useCases.create(request.origin(), request.destination());
        return OrderResponseDto.convert(response);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable UUID id) {
        Order response = useCases.getById(id);
        return OrderResponseDto.convert(response);
    }

    @PatchMapping("/{id}/status")
    public OrderResponseDto changeStatus(@PathVariable UUID id, @Valid @RequestBody ChangeOrderStatusRequestDto request) {
        Order response = useCases.changeStatus(id, request.status());
        return OrderResponseDto.convert(response);
    }

    @GetMapping
    public List<OrderResponseDto> list(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime createdFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime createdTo) {
        OrderFilter filter = new OrderFilter(status, origin, destination, createdFrom, createdTo);
        List<Order> orderList = useCases.list(filter);
        return orderList.stream().map(OrderResponseDto::convert).toList();
    }
}
