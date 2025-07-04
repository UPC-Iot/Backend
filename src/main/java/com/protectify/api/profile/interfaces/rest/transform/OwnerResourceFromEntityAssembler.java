package com.protectify.api.profile.interfaces.rest.transform;

import com.protectify.api.profile.domain.model.entities.Owner;
import com.protectify.api.profile.interfaces.rest.resources.OwnerResource;

public class OwnerResourceFromEntityAssembler {
    public static OwnerResource toResourceFromEntity(Owner owner) {
        return new OwnerResource(
                owner.getId(),
                owner.getUserId()
        );
    }
}