package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.port.in.DriverUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ApiError;
import com.ait.delivery.delivery.api.infrastructure.web.dto.DriverRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.DriverResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@Tag(name = "Drivers", description = "Gestion de conductores")
public class DriverController {

    private final DriverUseCases useCases;

    public DriverController(DriverUseCases useCases) {
        this.useCases = useCases;
    }

    @Operation(summary = "Crear un conductor")
    @ApiResponse(responseCode = "200", description = "Condcutor creado")
    @ApiResponse(responseCode = "404", description = "Datos de entrada no validos", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping
    public DriverResponseDto create(@Valid @RequestBody DriverRequestDto request) {
        Driver response = useCases.create(request.name(), request.licenseNumber());
        return DriverResponseDto.convert(response);
    }

    @Operation(summary = "Listar conductores activos")
    @ApiResponse(responseCode = "200", description = "Listado de conductores activos")
    @GetMapping("/active")
    public List<DriverResponseDto> listActive() {
        List<Driver> driverList = useCases.listActive();
        return driverList.stream().map(DriverResponseDto::convert).toList();
    }
}
