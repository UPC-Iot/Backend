package com.protectify.api.security.interfaces.rest;

import com.protectify.api.security.domain.model.entities.Device;
import com.protectify.api.security.domain.model.commands.DeleteDeviceCommand;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByHouseIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesByOwnerIdQuery;
import com.protectify.api.security.domain.model.queries.GetAllDevicesQuery;
import com.protectify.api.security.domain.model.queries.GetDeviceByIdQuery;
import com.protectify.api.security.domain.services.DeviceCommandService;
import com.protectify.api.security.domain.services.DeviceQueryService;
import com.protectify.api.security.interfaces.rest.resources.CreateDeviceResource;
import com.protectify.api.security.interfaces.rest.resources.DeviceResource;
import com.protectify.api.security.interfaces.rest.resources.UpdateDeviceResource;
import com.protectify.api.security.interfaces.rest.transform.CreateDeviceCommandFromResourceAssembler;
import com.protectify.api.security.interfaces.rest.transform.DeviceResourceFromEntityAssembler;
import com.protectify.api.security.interfaces.rest.transform.UpdateDeviceCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/devices", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Devices", description = "Device Management Endpoints")
public class DevicesController {
    private final DeviceCommandService deviceCommandService;
    private final DeviceQueryService deviceQueryService;

    public DevicesController(DeviceCommandService deviceCommandService, DeviceQueryService deviceQueryService) {
        this.deviceCommandService = deviceCommandService;
        this.deviceQueryService = deviceQueryService;
    }

    // DevicesController.java

    @GetMapping
    public ResponseEntity<List<DeviceResource>> getAllDevices() {
        var query = new GetAllDevicesQuery();
        var devices = deviceQueryService.handle(query);
        var resources = devices.stream().map(DeviceResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResource> getDeviceById(@PathVariable Long id) {
        var query = new GetDeviceByIdQuery(id);
        var device = deviceQueryService.handle(query);
        if (device.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<DeviceResource>> getAllDevicesByHouseId(@PathVariable Long houseId) {
        var query = new GetAllDevicesByHouseIdQuery(houseId);
        var devices = deviceQueryService.handle(query);
        var resources = devices.stream().map(DeviceResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<DeviceResource>> getAllDevicesByOwnerId(@PathVariable Long ownerId) {
        var query = new GetAllDevicesByOwnerIdQuery(ownerId);
        var devices = deviceQueryService.handle(query);
        var resources = devices.stream().map(DeviceResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<DeviceResource> createDevice(@RequestBody CreateDeviceResource resource) {
        var command = CreateDeviceCommandFromResourceAssembler.toCommandFromResource(resource);
        Long deviceId;
        try {
            deviceId = deviceCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        var device = deviceQueryService.handle(new GetDeviceByIdQuery(deviceId));
        if (device.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return new ResponseEntity<>(deviceResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResource> updateDevice(@PathVariable Long id, @RequestBody UpdateDeviceResource resource) {
        var command = UpdateDeviceCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<Device> device;
        try {
            device = deviceCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (device.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var deviceResource = DeviceResourceFromEntityAssembler.toResourceFromEntity(device.get());
        return ResponseEntity.ok(deviceResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable Long id) {
        var command = new DeleteDeviceCommand(id);
        try {
            deviceCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("Device with id " + id + " deleted successfully");
    }
}