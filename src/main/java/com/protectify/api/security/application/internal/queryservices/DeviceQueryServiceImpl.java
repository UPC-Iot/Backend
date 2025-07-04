package com.protectify.api.security.application.internal.queryservices;

import com.protectify.api.security.domain.model.entities.Device;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByOwnerIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesQuery;
import com.protectify.api.security.domain.model.queries.GetDeviceByIdQuery;
import com.protectify.api.security.domain.services.DeviceQueryService;
import com.protectify.api.security.infrastructure.persistence.jpa.repositories.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceQueryServiceImpl implements DeviceQueryService {
    private final DeviceRepository deviceRepository;

    public DeviceQueryServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Optional<Device> handle(GetDeviceByIdQuery query) {
        return deviceRepository.findById(query.id());
    }

    @Override
    public List<Device> handle(GetAllDevicesQuery query) {
        return deviceRepository.findAll();
    }

    @Override
    public List<Device> handle(GetAllDevicesByHouseIdQuery query) {
        return deviceRepository.findAllByHouse_Id(query.houseId());
    }

    @Override
    public List<Device> handle(GetAllDevicesByOwnerIdQuery query) {
        return deviceRepository.findAllByHouse_Owner_Id(query.ownerId());
    }
}