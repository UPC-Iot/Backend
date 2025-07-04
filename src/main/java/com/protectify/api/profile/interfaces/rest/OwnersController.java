package com.protectify.api.profile.interfaces.rest;

import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.domain.model.queries.GetAllOwnersQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetOwnerByUserIdQuery;
import com.protectify.api.profile.domain.services.OwnerQueryService;
import com.protectify.api.profile.interfaces.rest.resources.OwnerResource;
import com.protectify.api.profile.interfaces.rest.transform.OwnerResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1/owners", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Owners", description = "Owner Management Endpoints")
public class OwnersController {

    private final OwnerQueryService ownerQueryService;

    public OwnersController(OwnerQueryService ownerQueryService) {
        this.ownerQueryService = ownerQueryService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerResource>> getAllOwners() {
        var owners = ownerQueryService.handle(new GetAllOwnersQuery());
        var resources = owners.stream()
                .map(OwnerResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<OwnerResource> getOwnerByUserId(@PathVariable Long userId) {
        var owner = ownerQueryService.handle(new GetOwnerByUserIdQuery(userId));
        if (owner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = OwnerResourceFromEntityAssembler.toResourceFromEntity(owner.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResource> getOwnerById(@PathVariable Long id) {
        var owner = ownerQueryService.handle(new GetOwnerByIdQuery(id));
        if (owner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = OwnerResourceFromEntityAssembler.toResourceFromEntity(owner.get());
        return ResponseEntity.ok(resource);
    }
}