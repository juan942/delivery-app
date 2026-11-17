package com.ait.delivery.delivery.api.application.usecase;

import com.ait.delivery.delivery.api.domain.exception.BusinessRuleException;
import com.ait.delivery.delivery.api.domain.model.Driver;
import com.ait.delivery.delivery.api.domain.port.in.DriverUseCases;
import com.ait.delivery.delivery.api.domain.port.out.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DriverService implements DriverUseCases {

    private static final Logger log = LoggerFactory.getLogger(DriverService.class);
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver create(String name, String licenseNumber) {
        Optional<Driver> driverLicense = driverRepository.findByLicenseNumber(licenseNumber);

        if (driverLicense.isPresent()) {
            throw new BusinessRuleException("License already exist");
        }

        Driver newDriver = Driver.create(name, licenseNumber);
        Driver saved = driverRepository.save(newDriver);
        log.info("Driver created: id={}, licenseNumber={}", saved.getId(), saved.getLicenseNumber());
        return saved;
    }

    @Override
    public List<Driver> listActive() {
        return driverRepository.findAllActive();
    }
}
