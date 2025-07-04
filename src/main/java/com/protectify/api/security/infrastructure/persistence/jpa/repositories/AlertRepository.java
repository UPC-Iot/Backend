package com.protectify.api.security.infrastructure.persistence.jpa.repositories;

import com.protectify.api.security.domain.model.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findAllByHouse_Id(Long houseId);
    List<Alert> findAllByHouse_Owner_Id(Long ownerId);
}