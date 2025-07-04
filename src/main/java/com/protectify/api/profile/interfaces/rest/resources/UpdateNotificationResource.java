package com.protectify.api.profile.interfaces.rest.resources;

import java.time.LocalDateTime;

public record UpdateNotificationResource(
        String title,
        String message,
        LocalDateTime date
) {
}