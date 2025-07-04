package com.protectify.api.security.interfaces.rest;

import com.protectify.api.security.domain.model.entities.Visitor;
import com.protectify.api.security.domain.model.queries.*;
import com.protectify.api.security.domain.services.VisitorCommandService;
import com.protectify.api.security.domain.services.VisitorQueryService;
import com.protectify.api.security.interfaces.rest.resources.CreateVisitorResource;
import com.protectify.api.security.interfaces.rest.resources.UpdateVisitorResource;
import com.protectify.api.security.interfaces.rest.resources.VisitorResource;
import com.protectify.api.security.interfaces.rest.transform.CreateVisitorCommandFromResourceAssembler;
import com.protectify.api.security.interfaces.rest.transform.UpdateVisitorCommandFromResourceAssembler;
import com.protectify.api.security.interfaces.rest.transform.VisitorResourceFromEntityAssembler;
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
@RequestMapping(value = "api/v1/visitors", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Visitors", description = "Visitor Management Endpoints")
public class VisitorsController {
    private final VisitorCommandService visitorCommandService;
    private final VisitorQueryService visitorQueryService;

    public VisitorsController(VisitorCommandService visitorCommandService, VisitorQueryService visitorQueryService) {
        this.visitorCommandService = visitorCommandService;
        this.visitorQueryService = visitorQueryService;
    }

    @GetMapping
    public ResponseEntity<List<VisitorResource>> getAllVisitors() {
        var query = new GetAllVisitorsQuery();
        var visitors = visitorQueryService.handle(query);
        var resources = visitors.stream().map(VisitorResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitorResource> getVisitorById(@PathVariable Long id) {
        var query = new GetVisitorByIdQuery(id);
        var visitor = visitorQueryService.handle(query);
        if (visitor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = VisitorResourceFromEntityAssembler.toResourceFromEntity(visitor.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<VisitorResource>> getAllVisitorsByHouseId(@PathVariable Long houseId) {
        var query = new GetAllVisitorsByHouseIdQuery(houseId);
        var visitors = visitorQueryService.handle(query);
        var resources = visitors.stream().map(VisitorResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<VisitorResource>> getAllVisitorsByOwnerId(@PathVariable Long ownerId) {
        var query = new GetAllVisitorsByOwnerIdQuery(ownerId);
        var visitors = visitorQueryService.handle(query);
        var resources = visitors.stream().map(VisitorResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<VisitorResource> createVisitor(@RequestBody CreateVisitorResource resource) {
        var command = CreateVisitorCommandFromResourceAssembler.toCommandFromResource(resource);
        Long visitorId;
        try {
            visitorId = visitorCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        var visitor = visitorQueryService.handle(new GetVisitorByIdQuery(visitorId));
        if (visitor.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var visitorResource = VisitorResourceFromEntityAssembler.toResourceFromEntity(visitor.get());
        return new ResponseEntity<>(visitorResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitorResource> updateVisitor(@PathVariable Long id, @RequestBody UpdateVisitorResource resource) {
        var command = UpdateVisitorCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<Visitor> visitor;
        try {
            visitor = visitorCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (visitor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var visitorResource = VisitorResourceFromEntityAssembler.toResourceFromEntity(visitor.get());
        return ResponseEntity.ok(visitorResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVisitor(@PathVariable Long id) {
        try {
            visitorCommandService.handle(new com.protectify.api.security.domain.model.commands.DeleteVisitorCommand(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("Visitor with id " + id + " deleted successfully");
    }
}