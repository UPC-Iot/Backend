package com.protectify.api.security.domain.services;

import com.protectify.api.security.domain.model.entities.Device;
import com.protectify.api.security.domain.model.queries.GetAllDevicesQuery;
import com.protectify.api.security.domain.model.queries.GetDeviceByIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByOwnerIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    Optional<Device> handle(GetDeviceByIdQuery query);
    List<Device> handle(GetAllDevicesQuery query);
    List<Device> handle(GetAllDevicesByHouseIdQuery query);
    List<Device> handle(GetAllDevicesByOwnerIdQuery query);
}