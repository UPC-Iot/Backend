package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.UpdateVisitorCommand;
import com.protectify.api.security.interfaces.rest.resources.UpdateVisitorResource;

public class UpdateVisitorCommandFromResourceAssembler {
    public static UpdateVisitorCommand toCommandFromResource(Long id, UpdateVisitorResource resource) {
        return new UpdateVisitorCommand(
                id,
                resource.firstname(),
                resource.lastname(),
                resource.photo(),
                resource.role(),
                resource.lastVisit()
        );
    }
}