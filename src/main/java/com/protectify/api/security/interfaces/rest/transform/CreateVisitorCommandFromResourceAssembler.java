package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.CreateVisitorCommand;
import com.protectify.api.security.interfaces.rest.resources.CreateVisitorResource;

public class CreateVisitorCommandFromResourceAssembler {
    public static CreateVisitorCommand toCommandFromResource(CreateVisitorResource resource) {
        return new CreateVisitorCommand(
                resource.houseId(),
                resource.firstname(),
                resource.lastname(),
                resource.photo(),
                resource.role(),
                resource.lastVisit()
        );
    }
}