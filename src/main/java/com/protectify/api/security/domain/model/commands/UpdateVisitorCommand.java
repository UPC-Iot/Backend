package com.protectify.api.security.domain.model.commands;

import java.time.LocalDate;

public record UpdateVisitorCommand(
        Long id,
        String firstname,
        String lastname,
        String photo,
        String role,
        LocalDate lastVisit
) {}