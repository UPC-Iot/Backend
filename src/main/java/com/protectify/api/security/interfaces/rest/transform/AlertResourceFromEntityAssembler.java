package com.protectify.api.security.interfaces.rest.transform;

import com.protectify.api.security.domain.model.entities.Alert;
import com.protectify.api.security.interfaces.rest.resources.AlertResource;

public class AlertResourceFromEntityAssembler {
    public static AlertResource toResourceFromEntity(Alert alert) {
        return new AlertResource(
                alert.getId(),
                alert.getHouseId(),
                alert.getType().name(),
                alert.getMessage(),
                alert.getStatus().name(),
                alert.getTimestamp(),
                alert.getImageUrl()
        );
    }
}