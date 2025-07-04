package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.aggregates.Profile;
import com.protectify.api.profile.interfaces.rest.resources.ProfileResource;

public class ProfileResourceFromEntityAssembler {
    public static ProfileResource toResourceFromEntity(Profile profile) {
        return new ProfileResource(
                profile.getId(),
                profile.getUserId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getBirthDate(),
                profile.getDescription(),
                profile.getPhoto(),
                profile.getPhone(),
                profile.getAddress()
        );
    }
}