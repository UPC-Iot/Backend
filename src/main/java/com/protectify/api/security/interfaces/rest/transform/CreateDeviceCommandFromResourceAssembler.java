package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.CreateDeviceCommand;
import com.protectify.api.security.interfaces.rest.resources.CreateDeviceResource;

public class CreateDeviceCommandFromResourceAssembler {
    public static CreateDeviceCommand toCommandFromResource(CreateDeviceResource resource) {
        return new CreateDeviceCommand(
                resource.houseId(),
                resource.name(),
                resource.type(),
                resource.ipAddress(),
                resource.port(),
                resource.status(),
                resource.active(),
                resource.apiKey()
        );
    }
}