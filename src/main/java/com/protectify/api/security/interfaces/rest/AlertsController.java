package com.protectify.api.security.interfaces.rest;

import com.protectify.api.security.domain.model.commands.DeleteAlertCommand;
import com.protectify.api.security.domain.model.entities.Alert;
import com.protectify.api.security.domain.model.queries.*;
import com.protectify.api.security.domain.services.AlertCommandService;
import com.protectify.api.security.domain.services.AlertQueryService;
import com.protectify.api.security.interfaces.rest.resources.CreateAlertResource;
import com.protectify.api.security.interfaces.rest.resources.UpdateAlertResource;
import com.protectify.api.security.interfaces.rest.resources.AlertResource;
import com.protectify.api.security.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import com.protectify.api.security.interfaces.rest.transform.CreateAlertCommandFromResourceAssembler;
import com.protectify.api.security.interfaces.rest.transform.UpdateAlertCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Alert Management Endpoints")
public class AlertsController {
    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertsController(AlertCommandService alertCommandService, AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @GetMapping
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        var query = new GetAllAlertsQuery();
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream().map(AlertResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResource> getAlertById(@PathVariable Long id) {
        var query = new GetAlertByIdQuery(id);
        var alert = alertQueryService.handle(query);
        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<AlertResource>> getAllAlertsByHouseId(@PathVariable Long houseId) {
        var query = new GetAllAlertsByHouseIdQuery(houseId);
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream().map(AlertResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<AlertResource>> getAllAlertsByOwnerId(@PathVariable Long ownerId) {
        var query = new GetAllAlertsByOwnerIdQuery(ownerId);
        var alerts = alertQueryService.handle(query);
        var resources = alerts.stream().map(AlertResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<AlertResource> createAlert(@RequestBody CreateAlertResource resource) {
        var command = CreateAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        Long alertId;
        try {
            alertId = alertCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        var alert = alertQueryService.handle(new GetAlertByIdQuery(alertId));
        if (alert.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return new ResponseEntity<>(alertResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertResource> updateAlert(@PathVariable Long id, @RequestBody UpdateAlertResource resource) {
        var command = UpdateAlertCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<Alert> alert;
        try {
            alert = alertCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (alert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var alertResource = AlertResourceFromEntityAssembler.toResourceFromEntity(alert.get());
        return ResponseEntity.ok(alertResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlert(@PathVariable Long id) {
        var command = new DeleteAlertCommand(id);
        try {
            alertCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("Alerta con id " + id + " eliminada correctamente");
    }
}