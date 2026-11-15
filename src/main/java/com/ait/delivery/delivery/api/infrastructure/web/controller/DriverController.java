package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.port.in.DriverUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.DriverRequestDto;
import com.ait.delivery.delivery.api.infrastructure.web.dto.DriverResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
public class DriverController {

    private final DriverUseCases useCases;

    public DriverController(DriverUseCases useCases) {
        this.useCases = useCases;
    }

    @PostMapping
    public DriverResponseDto create(@Valid @RequestBody DriverRequestDto request) {
        Driver response = useCases.create(request.name(), request.licenseNumber());
        return DriverResponseDto.convert(response);
    }

    @GetMapping("/active")
    public List<DriverResponseDto> listActive() {
        List<Driver> driverList = useCases.listActive();
        return driverList.stream().map(DriverResponseDto::convert).toList();
    }
}
