package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.model.OrderFilter;
import com.ait.delivery.delivery.api.domain.port.in.OrderUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ApiError;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ChangeOrderStatusRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.OrderRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Gestion de ordenes")
public class OrderController {

    private final OrderUseCases useCases;

    public OrderController(OrderUseCases useCases) {
        this.useCases = useCases;
    }

    @Operation(summary = "Crear orden")
    @ApiResponse(responseCode = "201", description = "Orden creada")
    @ApiResponse(responseCode = "400", description = "Datos de entrada invalidos", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto request) {
        Order response = useCases.create(request.origin(), request.destination());
        return OrderResponseDto.convert(response);
    }

    @Operation(summary = "Obtener orden por ID")
    @ApiResponse(responseCode = "200", description = "Orden encontrada")
    @ApiResponse(responseCode = "404", description = "Orden no existe", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable UUID id) {
        Order response = useCases.getById(id);
        return OrderResponseDto.convert(response);
    }

    @Operation(summary = "Cambiar el estado de una orden")
    @ApiResponse(responseCode = "200", description = "Estado actualizado")
    @ApiResponse(responseCode = "404", description = "Orden no existe", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Transicion de estado invalida", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PatchMapping("/{id}/status")
    public OrderResponseDto changeStatus(@PathVariable UUID id, @Valid @RequestBody ChangeOrderStatusRequestDto request) {
        Order response = useCases.changeStatus(id, request.status());
        return OrderResponseDto.convert(response);
    }

    @Operation(summary = "Listar ordenes con filtros")
    @ApiResponse(responseCode = "200", description = "Listado de ordenes")
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
