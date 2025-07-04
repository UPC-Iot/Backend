package com.protectify.api.security.application.internal.outboundservices.acl;

import com.protectify.api.profile.domain.model.aggregates.Profile;
import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalProfilesService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfilesService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Optional<Owner> fetchOwnerById(Long ownerId) {
        return profilesContextFacade.fetchOwnerById(ownerId);
    }

    public Optional<Profile> fetchProfileByOwnerId(Long ownerId) {
        return profilesContextFacade.fetchProfileByOwnerId(ownerId);
    }
}