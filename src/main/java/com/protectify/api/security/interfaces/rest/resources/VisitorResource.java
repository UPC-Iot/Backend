package com.protectify.api.security.interfaces.rest.resources;

import java.time.LocalDate;

public record VisitorResource(
        Long id,
        Long houseId,
        String firstname,
        String lastname,
        String photo,
        String role,
        LocalDate lastVisit
) {
}