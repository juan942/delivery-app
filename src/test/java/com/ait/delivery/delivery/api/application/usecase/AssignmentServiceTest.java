package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.exception.BusinessRuleException;
import com.ait.delivery.delivery.api.domain.model.Assignment;
import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.model.FileUpload;
import com.ait.delivery.delivery.api.domain.model.Order;
import com.ait.delivery.delivery.api.domain.port.out.AssignmentRepository;
import com.ait.delivery.delivery.api.domain.port.out.DriverRepository;
import com.ait.delivery.delivery.api.domain.port.out.FileStorageRepository;
import com.ait.delivery.delivery.api.domain.port.out.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private FileStorageRepository fileStorageRepository;

    @InjectMocks
    private AssignmentService assignmentService;

    private final FileUpload pdf = new FileUpload("archivo.pdf", "application/pdf", new ByteArrayInputStream(new byte[0]));
    private final FileUpload image = new FileUpload("image.png", "image/png", new ByteArrayInputStream(new byte[0]));

    @Test
    void assign() {
        Order order = Order.create("CDMX", "Puebla");
        Driver driver = Driver.create("Juan Perez", "LIC-001");

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        when(assignmentRepository.findActiveByOrderId(order.getId())).thenReturn(Optional.empty());
        when(fileStorageRepository.store(any(FileUpload.class))).thenReturn("/uploads/archivo");
        when(assignmentRepository.save(any(Assignment.class))).thenAnswer(call -> call.getArgument(0));

        Assignment result = assignmentService.assign(order.getId(), driver.getId(), pdf, image);

        assertEquals(order.getId(), result.getOrderId());
        assertEquals(driver.getId(), result.getDriverId());
        assertTrue(result.isActive());
    }

    @Test
    void assignFailsWhenDriverIsInactive() {
        Order order = Order.create("CDMX", "Puebla");
        Driver driver = Driver.create("Juan Perez", "LIC-001");
        driver.deactivate();

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        when(assignmentRepository.findActiveByOrderId(order.getId())).thenReturn(Optional.empty());

        assertThrows(BusinessRuleException.class, () -> assignmentService.assign(order.getId(), driver.getId(), pdf, image));
    }

    @Test
    void assignFailsWhenOrderIsAssignedToSameDriver() {
        Order order = Order.create("CDMX", "Puebla");
        Driver driver = Driver.create("Juan Perez", "LIC-001");
        Assignment current = Assignment.create(order.getId(), driver.getId(), "/doc.pdf", "/img.png");

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(driverRepository.findById(driver.getId())).thenReturn(Optional.of(driver));
        when(assignmentRepository.findActiveByOrderId(order.getId())).thenReturn(Optional.of(current));

        assertThrows(BusinessRuleException.class, () -> assignmentService.assign(order.getId(), driver.getId(), pdf, image));
    }

}
