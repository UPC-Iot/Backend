package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.entities.Device;
import com.protectify.api.security.interfaces.rest.resources.DeviceResource;

public class DeviceResourceFromEntityAssembler {
    public static DeviceResource toResourceFromEntity(Device device) {
        return new DeviceResource(
                device.getId(),
                device.getHouseId(),
                device.getName(),
                device.getType().name(),
                device.getIpAddress(),
                device.getPort(),
                device.getStatus().name(),
                device.getActive(),
                device.getApiKey()
        );
    }
}