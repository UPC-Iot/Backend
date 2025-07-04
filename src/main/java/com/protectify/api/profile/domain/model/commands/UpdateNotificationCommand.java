package com.protectify.api.profile.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateNotificationCommand(
        Long id,
        String title,
        String message,
        LocalDateTime date
) {}