package com.protectify.api.profile.application.internal.commandservices;

import com.protectify.api.profile.application.internal.outboundservices.acl.ExternalUserService;
import com.protectify.api.profile.domain.exceptions.NotificationNotFoundException;
import com.protectify.api.profile.domain.exceptions.UserNotFoundException;
import com.protectify.api.profile.domain.model.entities.Notification;
import com.protectify.api.profile.domain.model.commands.CreateNotificationCommand;
import com.protectify.api.profile.domain.model.commands.UpdateNotificationCommand;
import com.protectify.api.profile.domain.model.commands.DeleteNotificationCommand;
import com.protectify.api.profile.domain.services.NotificationCommandService;
import com.protectify.api.profile.infrastructure.persistence.jpa.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;
    private final ExternalUserService externalUserService;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository, ExternalUserService externalUserService) {
        this.notificationRepository = notificationRepository;
        this.externalUserService = externalUserService;
    }

    @Override
    public Long handle(CreateNotificationCommand command) {
        var user = externalUserService.fetchUserById(command.userId());
        if (user.isEmpty()) {
            throw new UserNotFoundException(command.userId());
        }
        Notification notification = new Notification(command, user.get());
        notificationRepository.save(notification);
        return notification.getId();
    }

    @Override
    public Optional<Notification> handle(UpdateNotificationCommand command) {
        var notification = notificationRepository.findById(command.id());
        if (notification.isEmpty()) {
            return Optional.empty();
        }
        var notificationToUpdate = notification.get();
        Notification updatedNotification = notificationRepository.save(notificationToUpdate.update(command));
        return Optional.of(updatedNotification);
    }

    @Override
    public void handle(DeleteNotificationCommand command) {
        var notification = notificationRepository.findById(command.id());
        if (notification.isEmpty()) {
            throw new NotificationNotFoundException(command.id());
        }
        notificationRepository.delete(notification.get());
    }
}