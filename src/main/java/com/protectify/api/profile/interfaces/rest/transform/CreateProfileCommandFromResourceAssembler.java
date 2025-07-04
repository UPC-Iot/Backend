package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.commands.CreateProfileCommand;
import com.protectify.api.profile.interfaces.rest.resources.CreateProfileResource;

public class CreateProfileCommandFromResourceAssembler {
    public static CreateProfileCommand toCommandFromResource(CreateProfileResource resource) {
        return new CreateProfileCommand(
                resource.userId(),
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