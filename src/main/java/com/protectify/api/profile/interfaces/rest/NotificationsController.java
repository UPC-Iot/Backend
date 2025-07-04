package com.protectify.api.profile.interfaces.rest;

import com.protectify.api.profile.domain.model.entities.Notification;
import com.protectify.api.profile.domain.model.commands.DeleteNotificationCommand;
import com.protectify.api.profile.domain.model.queries.GetAllNotificationsByUserIdQuery;
import com.protectify.api.profile.domain.model.queries.GetAllNotificationsQuery;
import com.protectify.api.profile.domain.model.queries.GetNotificationByIdQuery;
import com.protectify.api.profile.domain.services.NotificationCommandService;
import com.protectify.api.profile.domain.services.NotificationQueryService;
import com.protectify.api.profile.interfaces.rest.resources.CreateNotificationResource;
import com.protectify.api.profile.interfaces.rest.resources.NotificationResource;
import com.protectify.api.profile.interfaces.rest.resources.UpdateNotificationResource;
import com.protectify.api.profile.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import com.protectify.api.profile.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.protectify.api.profile.interfaces.rest.transform.UpdateNotificationCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "api/v1/notifications", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "Notification Management Endpoints")
public class NotificationsController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;

    public NotificationsController(NotificationCommandService notificationCommandService, NotificationQueryService notificationQueryService) {
        this.notificationCommandService = notificationCommandService;
        this.notificationQueryService = notificationQueryService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        var query = new GetAllNotificationsQuery();
        var notifications = notificationQueryService.handle(query);
        var resources = notifications.stream().map(NotificationResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long id) {
        var query = new GetNotificationByIdQuery(id);
        var notification = notificationQueryService.handle(query);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResource>> getAllNotificationsByUserId(@PathVariable Long userId) {
        var query = new GetAllNotificationsByUserIdQuery(userId);
        var notifications = notificationQueryService.handle(query);
        var resources = notifications.stream().map(NotificationResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<NotificationResource> createNotification(@RequestBody CreateNotificationResource resource) {
        var command = CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource);
        Long notificationId;
        try {
            notificationId = notificationCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        var notification = notificationQueryService.handle(new GetNotificationByIdQuery(notificationId));
        if (notification.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return new ResponseEntity<>(notificationResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResource> updateNotification(@PathVariable Long id, @RequestBody UpdateNotificationResource resource) {
        var command = UpdateNotificationCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<Notification> notification;
        try {
            notification = notificationCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var notificationResource = NotificationResourceFromEntityAssembler.toResourceFromEntity(notification.get());
        return ResponseEntity.ok(notificationResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        var command = new DeleteNotificationCommand(id);
        try {
            notificationCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("Notification with id " + id + " deleted successfully");
    }
}