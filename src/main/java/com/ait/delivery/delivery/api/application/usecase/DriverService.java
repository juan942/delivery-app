package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.port.in.DriverUseCases;
import com.ait.delivery.delivery.api.domain.port.out.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DriverService implements DriverUseCases {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver create(String name, String licenseNumber) {
        Driver newDriver = Driver.create(name, licenseNumber);
        return driverRepository.save(newDriver);
    }

    @Override
    public List<Driver> listActive() {
        return driverRepository.findAllActive();
    }
}
