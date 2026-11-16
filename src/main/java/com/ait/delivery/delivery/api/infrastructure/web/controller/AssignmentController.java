package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.model.FileUpload;
import com.ait.delivery.delivery.api.domain.port.in.AssignmentUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.ApiError;
import com.ait.delivery.delivery.api.infrastructure.web.dto.AssignmentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
@Tag(name = "Assignments", description = "Asignacion de ordenes a conductores")
public class AssignmentController {

    private final AssignmentUseCases useCases;

    public AssignmentController(AssignmentUseCases useCases) {
        this.useCases = useCases;
    }

    @Operation(summary = "Realiza una asignacion de orden a conductor")
    @ApiResponse(responseCode = "201", description = "Asignacion creada")
    @ApiResponse(responseCode = "404", description = "Orden o conductor no encontrado", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @ApiResponse(responseCode = "409", description = "Orden no esta en estado CREATED, Conductor inactivo, Orden asignada previamente al conductor", content = @Content(schema = @Schema(implementation = ApiError.class)))
    @PostMapping(value = "/assign/{orderId}/{driverId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponseDto assign(
            @PathVariable UUID orderId,
            @PathVariable UUID driverId,
            @RequestParam MultipartFile pdf,
            @RequestParam MultipartFile image
            ) throws IOException {
        Assignment response = useCases.assign(orderId, driverId, toUpload(pdf), toUpload(image));
        return AssignmentResponseDto.convert(response);
    }

    @Operation(summary = "Consultar asignaciones con filtros")
    @ApiResponse(responseCode = "200", description = "Listado de asignaciones")
    @GetMapping
    public List<AssignmentResponseDto> list(@RequestParam(required = false) UUID orderId,
                                             @RequestParam(required = false) UUID driverId) {
        List<Assignment> response = useCases.list(orderId, driverId);
        return response.stream().map(AssignmentResponseDto::convert).toList();
    }

    private FileUpload toUpload(MultipartFile file) throws IOException {
        return new FileUpload(file.getOriginalFilename(), file.getContentType(), file.getInputStream());
    }
}
