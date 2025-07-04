package com.protectify.api.security.domain.model.commands;


public record CreateDeviceCommand(
        Long houseId,
        String name,
        String type,
        String ipAddress,
        Integer port,
        String status,
        Boolean active,
        String apiKey
) {}