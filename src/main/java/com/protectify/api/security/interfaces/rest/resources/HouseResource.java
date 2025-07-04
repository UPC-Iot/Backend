package com.protectify.api.security.interfaces.rest.resources;

public record HouseResource(
        Long id,
        Long ownerId,
        String address,
        String name,
        String description
) {
}