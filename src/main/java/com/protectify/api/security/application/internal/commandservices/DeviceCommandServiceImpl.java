package com.protectify.api.security.application.internal.commandservices;

import com.protectify.api.security.domain.exceptions.DeviceNotFoundException;
import com.protectify.api.security.domain.exceptions.HouseNotFoundException;
import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.CreateDeviceCommand;
import com.protectify.api.security.domain.model.commands.UpdateDeviceCommand;
import com.protectify.api.security.domain.model.commands.DeleteDeviceCommand;
import com.protectify.api.security.domain.model.entities.Device;
import com.protectify.api.security.domain.services.DeviceCommandService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.DeviceRepository;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {
    private final DeviceRepository deviceRepository;
    private final HouseRepository houseRepository;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository, HouseRepository houseRepository) {
        this.deviceRepository = deviceRepository;
        this.houseRepository = houseRepository;
    }

    @Override
    public Long handle(CreateDeviceCommand command) {
        Long houseId = command.houseId();
        Optional<House> house = houseRepository.findById(houseId);
        if (house.isEmpty()) {
            throw new HouseNotFoundException(houseId);
        }
        Device device = new Device(command, house.get());
        deviceRepository.save(device);
        return device.getId();
    }

    @Override
    public Optional<Device> handle(UpdateDeviceCommand command) {
        var device = deviceRepository.findById(command.id());
        if (device.isEmpty()) {
            return Optional.empty();
        }
        Device updatedDevice = deviceRepository.save(device.get().update(command));
        return Optional.of(updatedDevice);
    }

    @Override
    public void handle(DeleteDeviceCommand command) {
        var device = deviceRepository.findById(command.id());
        if (device.isEmpty()) {
            throw new DeviceNotFoundException(command.id());
        }
        deviceRepository.delete(device.get());
    }
}