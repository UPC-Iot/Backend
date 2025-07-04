package com.protectify.api.profile.infrastructure.persistence.jpa.repositories;

import com.protectify.api.profile.domain.model.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUser_Id(Long userId);
}