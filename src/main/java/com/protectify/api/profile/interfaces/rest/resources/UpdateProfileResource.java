package com.protectify.api.profile.interfaces.rest.resources;

import java.time.LocalDate;

public record UpdateProfileResource(
        String firstName,
        String lastName,
        LocalDate birthDate,
        String description,
        String photo,
        String phone,
        String address
) {
}