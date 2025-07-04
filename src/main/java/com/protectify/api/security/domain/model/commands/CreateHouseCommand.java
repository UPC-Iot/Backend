package com.protectify.api.security.domain.model.commands;

public record CreateHouseCommand(
        Long ownerId,
        String address,
        String name,
        String description
) {
}