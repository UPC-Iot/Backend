package com.protectify.api.security.domain.model.commands;

public record UpdateHouseCommand(
        Long id,
        String address,
        String name,
        String description
) {
}