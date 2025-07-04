package com.protectify.api.security.interfaces.rest.resources;

public record DeviceResource(
        Long id,
        Long houseId,
        String name,
        String type,
        String ipAddress,
        Integer port,
        String status,
        Boolean active,
        String apiKey
) {
}