package com.protectify.api.security.interfaces.rest.resources;

public record UpdateHouseResource(
        String address,
        String name,
        String description
) {
}