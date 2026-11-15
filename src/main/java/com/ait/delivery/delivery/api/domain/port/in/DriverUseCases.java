package com.ait.delivery.delivery.api.domain.port.in;

import com.ait.delivery.delivery.api.domain.model.Driver;

import java.util.List;

public interface DriverUseCases {
    Driver create(String name, String licenseNumber);
    List<Driver> listActive();
}
