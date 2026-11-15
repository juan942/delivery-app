package com.ait.delivery.delivery.api.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentEntity {
    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "ended_at")
    private ZonedDateTime endedAt;

    @Column(name = "pdf_path", nullable = false, length = 500)
    private String pdfPath;

    @Column(name = "image_path", nullable = false, length = 500)
    private String imagePath;
}
