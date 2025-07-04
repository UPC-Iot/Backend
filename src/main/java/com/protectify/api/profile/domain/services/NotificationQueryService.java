package com.protectify.api.profile.domain.services;

import com.protectify.api.profile.domain.model.entities.Notification;
import com.protectify.api.profile.domain.model.queries.GetAllNotificationsQuery;
import com.protectify.api.profile.domain.model.queries.GetNotificationByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetAllNotificationsByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface NotificationQueryService {
    Optional<Notification> handle(GetNotificationByIdQuery query);
    List<Notification> handle(GetAllNotificationsQuery query);
    List<Notification> handle(GetAllNotificationsByUserIdQuery query);
}