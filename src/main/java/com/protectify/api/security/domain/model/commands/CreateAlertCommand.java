package com.protectify.api.security.domain.model.commands;

import java.time.LocalDateTime;

public record CreateAlertCommand(
        Long houseId,
        String type,
        String message,
        String status,
        LocalDateTime timestamp,
        String imageUrl
) {}