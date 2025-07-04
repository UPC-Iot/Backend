package com.protectify.api.profile.domain.services;

import com.protectify.api.profile.domain.model.commands.CreateProfileCommand;
import com.protectify.api.profile.domain.model.commands.UpdateProfileCommand;
import com.protectify.api.profile.domain.model.commands.DeleteProfileCommand;
import com.protectify.api.profile.domain.model.aggregates.Profile;

import java.util.Optional;

public interface ProfileCommandService {
    Long handle(CreateProfileCommand command);
    Optional<Profile> handle(UpdateProfileCommand command);
    void handle(DeleteProfileCommand command);
}