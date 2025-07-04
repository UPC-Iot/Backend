package com.protectify.api.security.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateVisitorResource(
        Long id,
        String firstname,
        String lastname,
        String photo,
        String role,
        LocalDate lastVisit
) {
}