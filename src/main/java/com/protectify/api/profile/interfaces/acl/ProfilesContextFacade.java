package com.protectify.api.profile.interfaces.acl;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.model.aggregates.Profile;
import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.domain.model.commands.CreateOwnerCommand;
import com.protectify.api.profile.domain.model.queries.GetOwnerByIdQuery;
import com.protectify.api.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.protectify.api.profile.domain.services.OwnerCommandService;
import com.protectify.api.profile.domain.services.OwnerQueryService;
import com.protectify.api.profile.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfilesContextFacade {

    private final ProfileQueryService profileQueryService;
    private final OwnerCommandService ownerCommandService;
    private final OwnerQueryService ownerQueryService;

    public ProfilesContextFacade(ProfileQueryService profileQueryService,
                                 OwnerCommandService ownerCommandService,
                                 OwnerQueryService ownerQueryService) {
        this.profileQueryService = profileQueryService;
        this.ownerCommandService = ownerCommandService;
        this.ownerQueryService = ownerQueryService;
    }

    public Optional<Profile> fetchProfileByUserId(Long userId) {
        return profileQueryService.handle(new GetProfileByUserIdQuery(userId));
    }

    public Optional<Owner> fetchOwnerById(Long ownerId) {
        var getOwnerByIdQuery = new GetOwnerByIdQuery(ownerId);
        return ownerQueryService.handle(getOwnerByIdQuery);
    }

    public Optional<Profile> fetchProfileByOwnerId(Long ownerId) {
        var ownerQuery = new GetOwnerByIdQuery(ownerId);
        var owner = ownerQueryService.handle(ownerQuery);
        if (owner.isEmpty()) return Optional.empty();
        Long userId = owner.get().getUserId();
        var profileQuery = new GetProfileByUserIdQuery(userId);
        return profileQueryService.handle(profileQuery);
    }

    public Long createOwner(Long userId, User user) {
        return ownerCommandService.handle(new CreateOwnerCommand(userId), user);
    }
}