package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.commands.UpdateNotificationCommand;
import com.protectify.api.profile.interfaces.rest.resources.UpdateNotificationResource;

public class UpdateNotificationCommandFromResourceAssembler {
    public static UpdateNotificationCommand toCommandFromResource(Long id, UpdateNotificationResource resource) {
        return new UpdateNotificationCommand(
                id,
                resource.title(),
                resource.message(),
                resource.date()
        );
    }
}