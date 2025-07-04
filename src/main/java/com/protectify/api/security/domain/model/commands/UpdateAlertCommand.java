package com.protectify.api.security.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateAlertCommand(
        Long id,
        String type,
        String message,
        String status,
        LocalDateTime timestamp,
        String imageUrl
) {}