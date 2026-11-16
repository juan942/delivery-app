package com.ait.delivery.delivery.api.infrastructure.web.controller;

import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.model.FileUpload;
import com.ait.delivery.delivery.api.domain.port.in.AssignmentUseCases;
import com.ait.delivery.delivery.api.infrastructure.web.dto.AssignmentResponseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentUseCases useCases;

    public AssignmentController(AssignmentUseCases useCases) {
        this.useCases = useCases;
    }

    @PostMapping(value = "/assign/{orderId}/{driverId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AssignmentResponseDto assign(
            @PathVariable UUID orderId,
            @PathVariable UUID driverId,
            @RequestParam MultipartFile pdf,
            @RequestParam MultipartFile image
            ) throws IOException {
        Assignment response = useCases.assign(orderId, driverId, toUpload(pdf), toUpload(image));
        return AssignmentResponseDto.convert(response);
    }

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
