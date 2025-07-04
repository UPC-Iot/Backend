package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.CreateAlertCommand;
import com.protectify.api.security.interfaces.rest.resources.CreateAlertResource;

public class CreateAlertCommandFromResourceAssembler {
    public static CreateAlertCommand toCommandFromResource(CreateAlertResource resource) {
        return new CreateAlertCommand(
                resource.houseId(),
                resource.type(),
                resource.message(),
                resource.status(),
                resource.timestamp(),
                resource.imageUrl()
        );
    }
}