package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.CreateHouseCommand;
import com.protectify.api.security.interfaces.rest.resources.CreateHouseResource;

public class CreateHouseCommandFromResourceAssembler {
    public static CreateHouseCommand toCommandFromResource(CreateHouseResource resource) {
        return new CreateHouseCommand(
                resource.ownerId(),
                resource.address(),
                resource.name(),
                resource.description()

        );
    }
}