package com.protectify.api.security.infrastructure.persistence.jpa.repositories;

import com.protectify.api.security.domain.model.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByHouse_Id(Long houseId);
    List<Device> findAllByHouse_Owner_Id(Long ownerId);
}