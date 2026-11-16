package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.enums.OrderStatus;
import com.ait.delivery.delivery.api.domain.exception.BusinessRuleException;
import com.ait.delivery.delivery.api.domain.exception.DriverNotFoundException;
import com.ait.delivery.delivery.api.domain.exception.InvalidArgumentException;
import com.ait.delivery.delivery.api.domain.exception.OrderNotFoundException;
import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.model.FileUpload;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.port.in.AssignmentUseCases;
import com.ait.delivery.delivery.api.domain.port.out.AssignmentRepository;
import com.ait.delivery.delivery.api.domain.port.out.DriverRepository;
import com.ait.delivery.delivery.api.domain.port.out.FileStorageRepository;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AssignmentService implements AssignmentUseCases {
    private static final Set<String> PDF_EXTENSIONS = Set.of("pdf");
    private static final Set<String> PDF_TYPES = Set.of("application/pdf");
    private static final Set<String> IMAGE_EXTENSIONS = Set.of("png", "jpg", "jpeg");
    private static final Set<String> IMAGE_TYPES = Set.of("image/png", "image/jpeg");

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);
    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final AssignmentRepository assignmentRepository;
    private final FileStorageRepository fileStorageRepository;

    public AssignmentService(OrderRepository orderRepository, DriverRepository driverRepository, AssignmentRepository assignmentRepository, FileStorageRepository fileStorageRepository) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.assignmentRepository = assignmentRepository;
        this.fileStorageRepository = fileStorageRepository;
    }

    @Override
    public Assignment assign(UUID orderId, UUID driverId, FileUpload pdf, FileUpload image) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException(driverId));

        Optional<Assignment> currentAssigment = assignmentRepository.findActiveByOrderId(orderId);

        if (currentAssigment.isPresent() && currentAssigment.get().getDriverId().equals(driverId)) {
            throw new BusinessRuleException("Order is already assigned to driver");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BusinessRuleException("Order must be in CREATED status to be assigned");
        }

        if (!driver.isActive()) {
            throw new BusinessRuleException("Driver is not active");
        }

        validateTypeFile(pdf, PDF_EXTENSIONS, PDF_TYPES, "pdf");
        validateTypeFile(image, IMAGE_EXTENSIONS, IMAGE_TYPES, "image");

        String pdfPath = fileStorageRepository.store(pdf);
        String imagePath = fileStorageRepository.store(image);

        if (currentAssigment.isPresent()) {
            Assignment previous = currentAssigment.get();
            previous.close();
            assignmentRepository.save(previous);
            log.info("Assigment closed: id={}, orderId={}, previousDriverId={}", previous.getId(), orderId, previous.getDriverId());
        }

        Assignment newAssignment = Assignment.create(orderId, driverId, pdfPath, imagePath);
        Assignment saved = assignmentRepository.save(newAssignment);
        log.info("Driver assigned: assignId={}, orderId={}, driverId={}", saved.getId(), orderId, driverId);
        return saved;
    }

    @Override
    public List<Assignment> list(UUID orderId, UUID driverId) {
        return assignmentRepository.findAll(orderId, driverId);
    }

    private void validateTypeFile(FileUpload file, Set<String> extensions, Set<String> types, String field) {
        if (file == null) {
            throw new InvalidArgumentException("File '" + field + "' is required");
        }

        String extension = getExtensionFile(file.filename());
        if (!extensions.contains(extension) || !types.contains(file.contentType())) {
            throw new InvalidArgumentException("File '" + field + "' has an invalid type");
        }
    }

    private String getExtensionFile(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
