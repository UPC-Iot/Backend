package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.commands.UpdateProfileCommand;
import com.protectify.api.profile.interfaces.rest.resources.UpdateProfileResource;

public class UpdateProfileCommandFromResourceAssembler {
    public static UpdateProfileCommand toCommandFromResource(Long id, UpdateProfileResource resource) {
        return new UpdateProfileCommand(
                id,
                resource.firstName(),
                resource.lastName(),
                resource.birthDate(),
                resource.description(),
                resource.photo(),
                resource.phone(),
                resource.address()
        );
    }
}