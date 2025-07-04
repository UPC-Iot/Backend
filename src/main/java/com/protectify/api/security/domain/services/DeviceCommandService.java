package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.commands.CreateDeviceCommand;
import com.protectify.api.security.domain.model.commands.UpdateDeviceCommand;
import com.protectify.api.security.domain.model.commands.DeleteDeviceCommand;
import com.protectify.api.security.domain.model.entities.Device;

import java.util.Optional;

public interface DeviceCommandService {
    Long handle(CreateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceCommand command);
    void handle(DeleteDeviceCommand command);
}