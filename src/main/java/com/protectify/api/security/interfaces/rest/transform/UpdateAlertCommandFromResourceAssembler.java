package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.commands.UpdateAlertCommand;
import com.protectify.api.security.interfaces.rest.resources.UpdateAlertResource;

public class UpdateAlertCommandFromResourceAssembler {
    public static UpdateAlertCommand toCommandFromResource(Long id, UpdateAlertResource resource) {
        return new UpdateAlertCommand(
                id,
                resource.type(),
                resource.message(),
                resource.status(),
                resource.timestamp(),
                resource.imageUrl()
        );
    }
}