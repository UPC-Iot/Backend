package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.UpdateDeviceCommand;
import com.protectify.api.security.interfaces.rest.resources.UpdateDeviceResource;

public class UpdateDeviceCommandFromResourceAssembler {
    public static UpdateDeviceCommand toCommandFromResource(Long id, UpdateDeviceResource resource) {
        return new UpdateDeviceCommand(
                id,
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