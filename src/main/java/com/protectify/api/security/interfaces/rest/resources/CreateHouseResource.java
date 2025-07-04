package com.protectify.api.security.interfaces.rest.resources;

public record CreateHouseResource(
        Long ownerId,
        String address,
        String name,
        String description
) {
}