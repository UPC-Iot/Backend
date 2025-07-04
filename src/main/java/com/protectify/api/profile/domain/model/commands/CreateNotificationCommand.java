package com.protectify.api.profile.domain.model.commands;

import java.time.LocalDateTime;

public record CreateNotificationCommand(
        Long userId,
        String title,
        String message,
        LocalDateTime date
) {}