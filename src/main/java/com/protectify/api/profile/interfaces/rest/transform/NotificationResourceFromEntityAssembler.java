package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.entities.Notification;
import com.protectify.api.profile.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    public static NotificationResource toResourceFromEntity(Notification notification) {
        return new NotificationResource(
                notification.getId(),
                notification.getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getDate()
        );
    }
}