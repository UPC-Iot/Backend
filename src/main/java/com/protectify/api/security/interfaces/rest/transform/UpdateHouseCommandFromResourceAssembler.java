package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.UpdateHouseCommand;
import com.protectify.api.security.interfaces.rest.resources.UpdateHouseResource;

public class UpdateHouseCommandFromResourceAssembler {
    public static UpdateHouseCommand toCommandFromResource(Long id, UpdateHouseResource resource) {
        return new UpdateHouseCommand(
                id,
                resource.address(),
                resource.name(),
                resource.description()
        );
    }
}