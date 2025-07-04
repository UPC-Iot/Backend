package com.protectify.api.profile.interfaces.rest;

import com.protectify.api.profile.domain.model.aggregates.Profile;
import com.protectify.api.profile.domain.model.commands.DeleteProfileCommand;
import com.protectify.api.profile.domain.model.commands.UpdateProfileCommand;
import com.protectify.api.profile.domain.model.queries.GetAllProfilesQuery;
import com.protectify.api.profile.domain.model.queries.GetProfileByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.protectify.api.profile.domain.services.ProfileCommandService;
import com.protectify.api.profile.domain.services.ProfileQueryService;
import com.protectify.api.profile.interfaces.rest.resources.CreateProfileResource;
import com.protectify.api.profile.interfaces.rest.resources.ProfileResource;
import com.protectify.api.profile.interfaces.rest.resources.UpdateProfileResource;
import com.protectify.api.profile.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.protectify.api.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import com.protectify.api.profile.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping(value="api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    @GetMapping
    public ResponseEntity<List<ProfileResource>> getAllProfiles() {
        var getAllProfilesQuery = new GetAllProfilesQuery();
        var profiles = profileQueryService.handle(getAllProfilesQuery);
        var profileResources = profiles.stream().map(ProfileResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(profileResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long id) {
        var getProfileByIdQuery = new GetProfileByIdQuery(id);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        var getProfileByUserIdQuery = new GetProfileByUserIdQuery(userId);
        var profile = profileQueryService.handle(getProfileByUserIdQuery);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource createProfileResource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(createProfileResource);
        Long profileId;
        try {
            profileId = profileCommandService.handle(createProfileCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
        }
        if (profileId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var profile = profileQueryService.handle(new GetProfileByIdQuery(profileId));
        if (profile.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileResource> updateProfile(@PathVariable Long id,@RequestBody UpdateProfileResource updateProfileResource) {
        var updateProfileCommand = UpdateProfileCommandFromResourceAssembler.toCommandFromResource(id, updateProfileResource);
        Optional<Profile> profile;
        try{
            profile = profileCommandService.handle(updateProfileCommand);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        var deleteProfileCommand = new DeleteProfileCommand(id);
        try {
            profileCommandService.handle(deleteProfileCommand);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.ok("Profile with id " + id + " deleted successfully");
    }

}