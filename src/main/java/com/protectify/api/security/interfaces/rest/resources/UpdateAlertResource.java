package com.protectify.api.security.interfaces.rest.resources;

import java.time.LocalDateTime;

public record UpdateAlertResource(
        String type,
        String message,
        String status,
        LocalDateTime timestamp,
        String imageUrl
) {
}