package com.protectify.api.iam.application.internal.outboundservices.acl;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.interfaces.acl.ProfilesContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalProfileRoleService {
    private final ProfilesContextFacade profilesContextFacade;

    public ExternalProfileRoleService(ProfilesContextFacade profilesContextFacade) {
        this.profilesContextFacade = profilesContextFacade;
    }

    public Long createOwner(Long userId, User user) {
        return profilesContextFacade.createOwner(userId, user);
    }


}