package com.protectify.api.security.domain.model.commands;

import java.time.LocalDate;

public record CreateVisitorCommand(
        Long houseId,
        String firstname,
        String lastname,
        String photo,
        String role,
        LocalDate lastVisit
) {}