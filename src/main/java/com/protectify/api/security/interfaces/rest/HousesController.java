package com.protectify.api.security.interfaces.rest;

import com.protectify.api.security.domain.model.aggregates.House;
import com.protectify.api.security.domain.model.commands.DeleteHouseCommand;
import com.protectify.api.security.domain.model.queries.GetAllHousesQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByIdQuery;
import com.protectify.api.security.domain.model.queries.GetHouseByOwnerIdQuery;
import com.protectify.api.security.domain.services.HouseCommandService;
import com.protectify.api.security.domain.services.HouseQueryService;
import com.protectify.api.security.interfaces.rest.resources.CreateHouseResource;
import com.protectify.api.security.interfaces.rest.resources.HouseResource;
import com.protectify.api.security.interfaces.rest.resources.UpdateHouseResource;
import com.protectify.api.security.interfaces.rest.transform.CreateHouseCommandFromResourceAssembler;
import com.protectify.api.security.interfaces.rest.transform.HouseResourceFromEntityAssembler;
import com.protectify.api.security.interfaces.rest.transform.UpdateHouseCommandFromResourceAssembler;
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
@RequestMapping(value = "api/v1/houses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Houses", description = "House Management Endpoints")
public class HousesController {
    private final HouseCommandService houseCommandService;
    private final HouseQueryService houseQueryService;

    public HousesController(HouseCommandService houseCommandService, HouseQueryService houseQueryService) {
        this.houseCommandService = houseCommandService;
        this.houseQueryService = houseQueryService;
    }

    @GetMapping
    public ResponseEntity<List<HouseResource>> getAllHouses() {
        var query = new GetAllHousesQuery();
        var houses = houseQueryService.handle(query);
        var resources = houses.stream().map(HouseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResource> getHouseById(@PathVariable Long id) {
        var query = new GetHouseByIdQuery(id);
        var house = houseQueryService.handle(query);
        if (house.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var resource = HouseResourceFromEntityAssembler.toResourceFromEntity(house.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<HouseResource>> getHousesByOwnerId(@PathVariable Long ownerId) {
        var query = new GetHouseByOwnerIdQuery(ownerId);
        var houses = houseQueryService.handle(query);
        var resources = houses.stream().map(HouseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<HouseResource> createHouse(@RequestBody CreateHouseResource resource) {
        var command = CreateHouseCommandFromResourceAssembler.toCommandFromResource(resource);
        Long houseId;
        try {
            houseId = houseCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        var house = houseQueryService.handle(new GetHouseByIdQuery(houseId));
        if (house.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var houseResource = HouseResourceFromEntityAssembler.toResourceFromEntity(house.get());
        return new ResponseEntity<>(houseResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseResource> updateHouse(@PathVariable Long id, @RequestBody UpdateHouseResource resource) {
        var command = UpdateHouseCommandFromResourceAssembler.toCommandFromResource(id, resource);
        Optional<House> house;
        try {
            house = houseCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (house.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var houseResource = HouseResourceFromEntityAssembler.toResourceFromEntity(house.get());
        return ResponseEntity.ok(houseResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long id) {
        var command = new DeleteHouseCommand(id);
        try {
            houseCommandService.handle(command);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("House with id " + id + " deleted successfully");
    }
}