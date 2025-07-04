package com.protectify.api.profile.domain.services;

import com.protectify.api.iam.domain.model.aggregates.User;
import com.protectify.api.profile.domain.model.commands.CreateOwnerCommand;
import com.protectify.api.profile.domain.model.commands.DeleteOwnerCommand;

public interface OwnerCommandService {
    Long handle(CreateOwnerCommand command, User user);
    void handle(DeleteOwnerCommand command);
}