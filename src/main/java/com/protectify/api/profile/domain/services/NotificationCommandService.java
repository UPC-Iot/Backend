package com.protectify.api.profile.domain.services;

import com.protectify.api.profile.domain.model.commands.CreateNotificationCommand;
import com.protectify.api.profile.domain.model.commands.UpdateNotificationCommand;
import com.protectify.api.profile.domain.model.commands.DeleteNotificationCommand;
import com.protectify.api.profile.domain.model.entities.Notification;

import java.util.Optional;

public interface NotificationCommandService {
    Long handle(CreateNotificationCommand command);
    Optional<Notification> handle(UpdateNotificationCommand command);
    void handle(DeleteNotificationCommand command);
}