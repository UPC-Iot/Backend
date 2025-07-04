package com.protectify.api.profile.interfaces.rest.resources;

import java.time.LocalDateTime;

public record NotificationResource(
        Long id,
        Long userId,
        String title,
        String message,
        LocalDateTime date
) {
}