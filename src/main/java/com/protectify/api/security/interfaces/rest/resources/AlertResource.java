package com.protectify.api.security.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AlertResource(
        Long id,
        Long houseId,
        String type,
        String message,
        String status,
        LocalDateTime timestamp,
        String imageUrl
) {
}